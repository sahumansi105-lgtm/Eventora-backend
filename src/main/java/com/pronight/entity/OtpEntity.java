package com.pronight.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "otp_table")
public class OtpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String otp;

    public OtpEntity() {}

    public OtpEntity(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getOtp() {
        return otp;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}