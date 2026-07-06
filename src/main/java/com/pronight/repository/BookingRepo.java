package com.pronight.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pronight.entity.BookingEntity;

import jakarta.persistence.LockModeType;

@Repository
public interface BookingRepo extends JpaRepository<BookingEntity, Long> {

    // =========================
    // USER BOOKINGS
    // =========================
    List<BookingEntity> findByUserEmailOrderByCreatedAtDesc(String email);

    // =========================
    // RAZORPAY LOOKUPS
    // =========================
    Optional<BookingEntity> findByRazorpayOrderId(String razorpayOrderId);

    Optional<BookingEntity> findByRazorpayPaymentId(String razorpayPaymentId);

    // =========================
    // STATUS FILTER
    // =========================
    List<BookingEntity> findByBookingStatus(BookingEntity.BookingStatus bookingStatus);

    // =========================
    // USER LIMIT CHECK (SAFE SUM)
    // =========================
    @Query("""
        SELECT COALESCE(SUM(b.quantity), 0)
        FROM BookingEntity b
        WHERE b.user.userId = :userId
        AND b.pass.passId = :passId
        AND b.bookingStatus IN :statuses
    """)
    Long getTotalBookedTickets(
            @Param("userId") Long userId,
            @Param("passId") Long passId,
            @Param("statuses") List<BookingEntity.BookingStatus> statuses
    );

    @Query("""
        SELECT COALESCE(SUM(b.quantity), 0)
        FROM BookingEntity b
        WHERE b.pass.passId = :passId
        AND b.bookingStatus IN :statuses
    """)
    Long getTotalTicketsForPass(
            @Param("passId") Long passId,
            @Param("statuses") List<BookingEntity.BookingStatus> statuses
    );

    // =========================
    // PENDING BOOKINGS COUNT
    // =========================
    @Query("""
        SELECT COUNT(b)
        FROM BookingEntity b
        WHERE b.pass.passId = :passId
        AND b.bookingStatus = com.pronight.entity.BookingEntity.BookingStatus.PENDING
    """)
    Long countPendingBookings(@Param("passId") Long passId);

    // =========================
    // 🔥 OPTIONAL (ADVANCED - USED FOR LOCKING BOOKINGS IF NEEDED)
    // =========================
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BookingEntity b WHERE b.bookingId = :id")
    Optional<BookingEntity> findByIdForUpdate(@Param("id") Long id);
}
