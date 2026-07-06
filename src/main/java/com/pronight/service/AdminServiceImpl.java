package com.pronight.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.pronight.dto.AdminDashboardDTO;
import com.pronight.dto.LowStockPassDTO;
import com.pronight.dto.RecentBookingDTO;
import com.pronight.entity.BookingEntity;
import com.pronight.entity.BookingEntity.BookingStatus;
import com.pronight.entity.PassEntity;
import com.pronight.entity.UserEntity;
import com.pronight.repository.BookingRepo;
import com.pronight.repository.EventRepo;
import com.pronight.repository.PassRepo;
import com.pronight.repository.UserRepo;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private PassRepo passRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private EmailService emailService;

    @Override
    public AdminDashboardDTO getDashboard() {

        AdminDashboardDTO dashboard = new AdminDashboardDTO();

        dashboard.setTotalUsers(userRepo.count());

        dashboard.setTotalEvents(eventRepo.count());

        dashboard.setTotalPasses(passRepo.count());

        dashboard.setTotalBookings(bookingRepo.count());

        Double revenue = bookingRepo.findAll()
                .stream()
                .mapToDouble(BookingEntity::getTotalAmount)
                .sum();

        dashboard.setTotalRevenue(revenue);

        List<RecentBookingDTO> recentBookings = bookingRepo.findAll()
                .stream()
                .sorted(
                        Comparator.comparing(BookingEntity::getCreatedAt)
                                .reversed())
                .limit(5)
                .map(b -> {

                    RecentBookingDTO dto = new RecentBookingDTO();

                    dto.setBookingId(b.getBookingId());

                    dto.setUserName(b.getUser().getFullName());

                    dto.setEventTitle(b.getEvent().getTitle());

                    dto.setQuantity(b.getQuantity());

                    dto.setTotalAmount(b.getTotalAmount());

                    dto.setStatus(
                            b.getBookingStatus() != null
                                    ? b.getBookingStatus().name()
                                    : "PENDING"
                    );

                    return dto;

                })
                .toList();

        dashboard.setRecentBookings(recentBookings);

        List<LowStockPassDTO> lowStock = passRepo
                .findByAvailableQuantityLessThanEqual(10)
                .stream()
                .map(p -> {

                    LowStockPassDTO dto = new LowStockPassDTO();

                    dto.setPassId(p.getPassId());

                    dto.setPassName(p.getPassName());

                    dto.setEventTitle(p.getEvent().getTitle());

                    dto.setAvailableQuantity(p.getAvailableQuantity());

                    return dto;

                })
                .toList();

        dashboard.setLowStockPasses(lowStock);

        return dashboard;
    }

    @Override
    public void confirmBooking(Long bookingId) {

        BookingEntity booking = bookingRepo
                .findById(bookingId)
                .orElseThrow();

        booking.setBookingStatus(
                BookingEntity.BookingStatus.CONFIRMED);

        bookingRepo.save(booking);

        emailService.sendBookingConfirmedMail(
                booking.getUser().getEmail(),
                booking.getUser().getFullName(),
                booking.getEvent().getTitle()
        );
    }

    @Override
    public void cancelBooking(Long bookingId) {

        BookingEntity booking = bookingRepo
                .findById(bookingId)
                .orElseThrow();

        booking.setBookingStatus(
                BookingEntity.BookingStatus.CANCELLED);

        bookingRepo.save(booking);

        emailService.sendBookingCancelledMail(
                booking.getUser().getEmail(),
                booking.getUser().getFullName(),
                booking.getEvent().getTitle()
        );
    }
    
    @Override
    public List<BookingEntity> getAllBookings() {

        return bookingRepo.findAll()
                .stream()
                .sorted(
                        Comparator.comparing(BookingEntity::getCreatedAt)
                                .reversed()
                )
                .toList();
    }
    
    @Override
    public List<UserEntity> getAllUsers() {

        return userRepo.findByActiveTrue();

    }

    @Override
    public void deleteUser(Long userId) {

        UserEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        try {
            userRepo.deleteById(userId);
            userRepo.flush();
        } catch (DataIntegrityViolationException ex) {
            user.setActive(false);
            userRepo.save(user);
        }

    }

    @Override
    public List<BookingEntity> getCancellationRequests() {

        return bookingRepo.findByBookingStatus(
                BookingStatus.CANCELLATION_REQUESTED
        );

    }

    @Override
    public void approveCancellation(Long bookingId) {

        BookingEntity booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getBookingStatus() != BookingStatus.CANCELLATION_REQUESTED) {
            throw new RuntimeException("No cancellation request found.");
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);

        PassEntity pass = booking.getPass();

        pass.setAvailableQuantity(
                pass.getAvailableQuantity() + booking.getQuantity()
        );

        pass.setSoldQuantity(
                pass.getSoldQuantity() - booking.getQuantity()
        );

        passRepo.save(pass);

        bookingRepo.save(booking);

        emailService.sendBookingCancelledMail(
                booking.getUser().getEmail(),
                booking.getUser().getFullName(),
                booking.getEvent().getTitle()
        );
    }

    @Override
    public void rejectCancellation(Long bookingId) {

        BookingEntity booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getBookingStatus() != BookingStatus.CANCELLATION_REQUESTED) {
            throw new RuntimeException("No cancellation request found.");
        }

        booking.setBookingStatus(BookingStatus.CONFIRMED);

        bookingRepo.save(booking);
    }
    
    public void requestCancellation(Long bookingId, String email) {

        BookingEntity booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        if (booking.getBookingStatus() != BookingStatus.CONFIRMED) {
            throw new RuntimeException(
                    "Only confirmed bookings can be cancelled."
            );
        }

        booking.setBookingStatus(BookingStatus.CANCELLATION_REQUESTED);

        bookingRepo.save(booking);
    }

}
