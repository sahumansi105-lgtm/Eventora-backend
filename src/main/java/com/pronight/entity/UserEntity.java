package com.pronight.entity;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @Column(nullable = false)
    private String role; // ADMIN, USER

    
    private String membership_level;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer loyaltyPoints;

    private String interests;
   

	@Column(nullable = false)
    private LocalDateTime registrationDate;

	@PrePersist
	public void prePersist() {
		
		

	    if (role == null || role.isBlank()) {
	        role = "USER";
	    }

	    if (membership_level == null || membership_level.isBlank()) {
	        membership_level = "BRONZE";
	    }

	    if (loyaltyPoints == null) {
	        loyaltyPoints = 0;
	    }

	    if (active == null) {
	        active = true;
	    }

	    registrationDate = LocalDateTime.now();
	}


	 // Getters & Setters
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}



	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}


	public String getMembership_level() {
	    return membership_level;
	}

	public void setMembership_level(String membership_level) {
	    this.membership_level = membership_level;
	}

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
		
	public String getInterests() {
		return interests;
	}


	public void setInterests(String interests) {
		this.interests = interests;
	}


	@Override
	public String toString() {
	    return "UserEntity [userId=" + userId + ", fullName=" + fullName + ", email=" + email
	            + ", phoneNumber=" + phoneNumber + ", role=" + role 
	            +  ", membership_level=" + membership_level
	            +  ", loyaltyPoints=" + loyaltyPoints
	            + ", registrationDate=" + registrationDate + "]";
	}

	public UserEntity(Long userId, String fullName, String email, String password, String phoneNumber, String role,
			            String membership_level, Integer loyaltyPoints, LocalDateTime registrationDate) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.membership_level = membership_level;
		this.loyaltyPoints = loyaltyPoints;
		this.registrationDate = registrationDate;
	}

	public UserEntity() {
		super();
	}



   
   	
}
