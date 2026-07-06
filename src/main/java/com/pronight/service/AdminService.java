package com.pronight.service;

import java.util.List;

import com.pronight.dto.AdminDashboardDTO;
import com.pronight.entity.BookingEntity;
import com.pronight.entity.UserEntity;

public interface AdminService {

    AdminDashboardDTO getDashboard();

    List<BookingEntity> getAllBookings();

    void confirmBooking(Long bookingId);

    void cancelBooking(Long bookingId);

    List<UserEntity> getAllUsers();

    void deleteUser(Long userId);

    // Get all cancellation requests
    List<BookingEntity> getCancellationRequests();

    // Approve cancellation request
    void approveCancellation(Long bookingId);

    // Reject cancellation request
    void rejectCancellation(Long bookingId);

}