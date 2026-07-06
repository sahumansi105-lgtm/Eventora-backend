package com.pronight.service;

import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pronight.entity.BookingEntity;
import com.pronight.entity.BookingEntity.BookingStatus;
import com.pronight.entity.PassEntity;
import com.pronight.entity.UserEntity;
import com.pronight.entity.WaitlistEntity;
import com.pronight.repository.BookingRepo;
import com.pronight.repository.PassRepo;
import com.pronight.repository.UserRepo;
import com.pronight.repository.WaitlistRepo;
import com.pronight.util.QRGenerator;
import com.razorpay.Order;

@Transactional
@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepository;

    @Autowired
    private PassRepo passRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private RazorpayService razorpayService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TicketPDFService ticketPDFService;

    @Autowired
    private PassService passService;

    @Autowired
    private WaitlistRepo waitlistRepository;

    // =========================
    // CREATE BOOKING (LIVE DEDUCTION)
    // =========================
    public BookingEntity createBooking(String userEmail, Long passId, Integer qty) throws Exception {

        if (qty == null || qty <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 LOCK PASS ROW
        PassEntity pass = passRepository.findByIdForUpdate(passId)
                .orElseThrow(() -> new RuntimeException("Pass not found"));

        pass = passService.refreshAvailability(pass);

        int available = pass.getAvailableQuantity() == null ? 0 : pass.getAvailableQuantity();

        // =========================
        // USER LIMIT CHECK
        // =========================
        Long activeBooked = bookingRepository.getTotalBookedTickets(
                user.getUserId(),
                passId,
                Arrays.asList(
                        BookingStatus.PENDING,
                        BookingStatus.CONFIRMED,
                        BookingStatus.CANCELLATION_REQUESTED
                )
        );

        long alreadyBooked = activeBooked == null ? 0 : activeBooked;

        int maxPerUser = pass.getMaxPerUser() == null ? 10 : pass.getMaxPerUser();
        int maxPerBooking = pass.getMaxPerBooking() == null ? 10 : pass.getMaxPerBooking();
        int minPerBooking = pass.getMinPerBooking() == null ? 1 : pass.getMinPerBooking();

        if (qty < minPerBooking) {
            throw new RuntimeException("Minimum " + minPerBooking + " tickets required");
        }

        if (qty > maxPerBooking) {
            throw new RuntimeException("Maximum " + maxPerBooking + " tickets per booking");
        }

        if (alreadyBooked + qty > maxPerUser) {
            throw new RuntimeException("Max " + maxPerUser + " tickets allowed per user");
        }

        // =========================
        // WAITLIST LOGIC
        // =========================
        if (available == 0) {

            WaitlistEntity wait = new WaitlistEntity();
            wait.setUser(user);
            wait.setPass(pass);
            wait.setQuantity(qty);
            wait.setStatus("WAITING");
            wait.setWaitlistPosition((int) waitlistRepository.countByPass(pass) + 1);

            waitlistRepository.save(wait);

            throw new RuntimeException("SOLD OUT - Added to waitlist");
        }

        if (available < qty) {
            throw new RuntimeException("Only " + available + " tickets available");
        }

        // =========================
        // PAYMENT ORDER
        // =========================
        Double amount = resolvePrice(pass) * qty;
        Order order = razorpayService.createOrder(amount);

        // =========================
        // CREATE BOOKING
        // =========================
        BookingEntity booking = new BookingEntity();
        booking.setUser(user);
        booking.setPass(pass);
        booking.setEvent(pass.getEvent());
        booking.setQuantity(qty);
        booking.setTotalAmount(amount);
        booking.setRazorpayOrderId(order.get("id"));
        booking.setBookingStatus(BookingStatus.PENDING);

        BookingEntity saved = bookingRepository.save(booking);
        passService.refreshAvailability(pass);

        return saved;
    }

    // =========================
    // PAYMENT SUCCESS
    // =========================
    public BookingEntity updatePaymentSuccess(String orderId, String paymentId, String signature) {

        BookingEntity booking = bookingRepository.findByRazorpayOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getBookingStatus() == BookingStatus.CONFIRMED) {
            return booking;
        }

        if (!razorpayService.verifySignature(orderId, paymentId, signature)) {
            throw new RuntimeException("Invalid payment signature");
        }

        booking.setRazorpayPaymentId(paymentId);
        booking.setRazorpaySignature(signature);
        booking.setBookingStatus(BookingStatus.CONFIRMED);

        booking.setQrCode(QRGenerator.generateQR(
                "BOOKING:" + booking.getBookingId() + "|" +
                        booking.getPass().getPassId() + "|" +
                        booking.getEvent().getEventId() + "|" +
                        booking.getUser().getFullName()
        ));

        BookingEntity saved = bookingRepository.save(booking);
        passService.refreshAvailability(saved.getPass());

        // EMAIL TICKET (safe try-catch)
        try {
            byte[] pdf = ticketPDFService.generateTicket(saved);

            emailService.sendTicketEmail(
                    saved.getUser().getEmail(),
                    saved.getUser().getFullName(),
                    saved.getEvent().getTitle(),
                    pdf
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return saved;
    }

    // =========================
    // RELEASE PENDING BOOKING
    // =========================
    public void releaseIfPending(Long bookingId) {

        BookingEntity booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            return;
        }

        PassEntity pass = passRepository.findByIdForUpdate(booking.getPass().getPassId())
                .orElseThrow(() -> new RuntimeException("Pass not found"));

        booking.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        passService.refreshAvailability(pass);
    }

    // =========================
    // GET USER BOOKINGS
    // =========================
    public List<BookingEntity> getBookingsForUser(String email) {
        return bookingRepository.findByUserEmailOrderByCreatedAtDesc(email);
    }

    // =========================
    // GET BY ID
    // =========================
    public BookingEntity getById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public String validateTicket(Long bookingId, Long passId, Long eventId) {
        BookingEntity booking = bookingRepository.findByIdForUpdate(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            return "Invalid Ticket - payment is not confirmed";
        }

        if (passId != null && !booking.getPass().getPassId().equals(passId)) {
            return "Invalid Ticket - pass mismatch";
        }

        if (eventId != null && !booking.getEvent().getEventId().equals(eventId)) {
            return "Invalid Ticket - event mismatch";
        }

        if (booking.getCheckedInAt() != null) {
            return "Ticket already used at " + booking.getCheckedInAt();
        }

        booking.setCheckedInAt(LocalDateTime.now());
        bookingRepository.save(booking);

        return "Valid Ticket - Entry Allowed";
    }

    // =========================
    // PRICE LOGIC
    // =========================
    private Double resolvePrice(PassEntity pass) {

        int sold = pass.getSoldQuantity() == null ? 0 : pass.getSoldQuantity();

        if (pass.getFirstTierLimit() != null &&
                pass.getFirstTierPrice() != null &&
                sold < pass.getFirstTierLimit()) {
            return pass.getFirstTierPrice();
        }

        if (pass.getSecondTierLimit() != null &&
                pass.getSecondTierPrice() != null &&
                sold < pass.getSecondTierLimit()) {
            return pass.getSecondTierPrice();
        }

        return pass.getFinalTierPrice() != null
                ? pass.getFinalTierPrice()
                : pass.getPrice();
    }
}
