package com.pronight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pronight.dto.AdminDashboardDTO;
import com.pronight.entity.BookingEntity;
import com.pronight.entity.UserEntity;
import com.pronight.service.AdminService;


@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    


    @GetMapping("/dashboard")
    public AdminDashboardDTO getDashboard() {

        return adminService.getDashboard();

    }
    
    @GetMapping("/bookings")
    public List<BookingEntity> getAllBookings() {

        return adminService.getAllBookings();

    }
    
    @PutMapping("/bookings/{id}/confirm")
    public String confirmBooking(@PathVariable Long id){

        adminService.confirmBooking(id);

        return "Booking Confirmed";
    }


    @PutMapping("/bookings/{id}/cancel")
    public String cancelBooking(@PathVariable Long id){

        adminService.cancelBooking(id);

        return "Booking Cancelled";
    }
    
    @GetMapping("/users")
    public List<UserEntity> getAllUsers() {

        return adminService.getAllUsers();

    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {

        adminService.deleteUser(id);

        return "User Deleted";
    }
    
    // cancel bookings 
    @GetMapping("/cancellation-requests")
    public List<BookingEntity> getCancellationRequests() {

        return adminService.getCancellationRequests();

    }

    @PutMapping("/approve-cancellation/{bookingId}")
    public ResponseEntity<String> approveCancellation(
            @PathVariable Long bookingId) {

        adminService.approveCancellation(bookingId);

        return ResponseEntity.ok("Cancellation approved");
    }

    @PutMapping("/reject-cancellation/{bookingId}")
    public ResponseEntity<String> rejectCancellation(
            @PathVariable Long bookingId) {

        adminService.rejectCancellation(bookingId);

        return ResponseEntity.ok("Cancellation rejected");
    }
}