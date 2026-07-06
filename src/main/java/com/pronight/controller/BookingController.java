package com.pronight.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pronight.entity.BookingEntity;
import com.pronight.service.BookingService;
import com.pronight.service.TicketPDFService;

@RestController
@RequestMapping("/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private TicketPDFService ticketPDFService;

    @PostMapping("/create")
    public BookingEntity createBooking(
            @RequestParam Long passId,
            @RequestParam Integer qty,
            Principal principal
    ) throws Exception {
        return bookingService.createBooking(principal.getName(), passId, qty);
    }

    @GetMapping("/my")
    public List<BookingEntity> getMyBookings(Principal principal) {
        return bookingService.getBookingsForUser(principal.getName());
    }

    @PostMapping("/payment/success")
    public BookingEntity paymentSuccess(
            @RequestParam String orderId,
            @RequestParam String paymentId,
            @RequestParam String signature
    ) {
        return bookingService.updatePaymentSuccess(orderId, paymentId, signature);
    }

    @PostMapping("/payment/failure")
    public ResponseEntity<String> paymentFailure(@RequestParam Long bookingId) {
        bookingService.releaseIfPending(bookingId);
        return ResponseEntity.ok("Payment failed. Booking released.");
    }

    @GetMapping("/{id}/ticket")
    public ResponseEntity<byte[]> downloadTicket(@PathVariable Long id) {
        BookingEntity booking = bookingService.getById(id);
        byte[] pdf = ticketPDFService.generateTicket(booking);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=ProNight-Ticket-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PostMapping("/validate")
    public String validate(@RequestBody Map<String, String> body) {
        try {
            String qr = body.get("qrData");

            if (qr == null || !qr.startsWith("BOOKING:")) {
                return "Invalid QR";
            }

            String[] parts = qr.replace("BOOKING:", "").split("\\|");

            if (parts.length < 3) {
                return "Invalid QR Format";
            }

            Long bookingId = Long.parseLong(parts[0]);
            Long passId = Long.parseLong(parts[1]);
            Long eventId = Long.parseLong(parts[2]);

            return bookingService.validateTicket(bookingId, passId, eventId);
        } catch (Exception e) {
            return "Invalid QR / Booking Not Found";
        }
    }

    @PutMapping("/release/{bookingId}")
    public ResponseEntity<String> release(@PathVariable Long bookingId) {
        bookingService.releaseIfPending(bookingId);
        return ResponseEntity.ok("Booking released successfully");
    }
}
