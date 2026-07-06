	package com.pronight.entity;
	
	import java.time.LocalDateTime;
	
	import jakarta.persistence.*;
	
	@Entity
	@Table(name = "wishlist")
	public class WishlistEntity {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long wishlistId;
	
	    @ManyToOne
	    @JoinColumn(name = "user_id", nullable = false)
	    private UserEntity user;
	
	    @ManyToOne
	    @JoinColumn(name = "event_id", nullable = false)
	    private EventEntity event;
	
	    private LocalDateTime createdAt = LocalDateTime.now();
	
	
	
	    public Long getWishlistId() {
	        return wishlistId;
	    }
	
	    public void setWishlistId(Long wishlistId) {
	        this.wishlistId = wishlistId;
	    }
	
	    public UserEntity getUser() {
	        return user;
	    }
	
	    public void setUser(UserEntity user) {
	        this.user = user;
	    }
	
	    public EventEntity getEvent() {
	        return event;
	    }
	
	    public void setEvent(EventEntity event) {
	        this.event = event;
	    }
	
	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }
	
	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }
	}