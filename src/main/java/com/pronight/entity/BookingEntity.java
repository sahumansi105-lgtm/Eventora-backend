package com.pronight.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "bookings")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private PassEntity pass;

    @ManyToOne
    private EventEntity event;

    private Integer quantity;

    private Double totalAmount;


    // Booking Status Enum

    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        CANCELLATION_REQUESTED,
        CANCELLED

    }


    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;
    
    @Lob
    private String qrCode;


    // Razorpay Details

    private String razorpayOrderId;

    private String razorpayPaymentId;

    private String razorpaySignature;


    private LocalDateTime createdAt;

    private LocalDateTime checkedInAt;


    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        if (bookingStatus == null) {

            bookingStatus = BookingStatus.PENDING;

        }
        
        

    }


    // Getters Setters


    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PassEntity getPass() {
        return pass;
    }

    public void setPass(PassEntity pass) {
        this.pass = pass;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public LocalDateTime getCheckedInAt() {
        return checkedInAt;
    }

    public void setCheckedInAt(LocalDateTime checkedInAt) {
        this.checkedInAt = checkedInAt;
    }

}
