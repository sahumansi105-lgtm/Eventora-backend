package com.pronight.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "waitlist")
public class WaitlistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long waitlistId;

    @ManyToOne
    private UserEntity user;

    @ManyToOne
    private PassEntity pass;

    private Integer quantity;

    private Integer waitlistPosition;

    private LocalDateTime createdAt;

    private String status; // WAITING, PROMOTED

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();

        if(status == null) {
            status = "WAITING";
        }
    }

    // getters setters
	public Long getWaitlistId() {
		return waitlistId;
	}

	public void setWaitlistId(Long waitlistId) {
		this.waitlistId = waitlistId;
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getWaitlistPosition() {
		return waitlistPosition;
	}

	public void setWaitlistPosition(Integer waitlistPosition) {
		this.waitlistPosition = waitlistPosition;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


    
    
}
