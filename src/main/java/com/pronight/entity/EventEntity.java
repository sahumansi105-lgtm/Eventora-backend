package com.pronight.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 50)
    private String category;          // DJ_NIGHT, CONCERT, FESTIVAL, CELEBRITY, COLLEGE_FEST

    @Column(nullable = false, length = 50)
    private String season;          // HOLI, DIWALI, NEW_YEAR, MONSOON, SUMMER

    @Column(length = 50)
    private String mood;         // PARTY, FAMILY, ROMANTIC, PREMIUM, COLLEGE

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 200)
    private String venue;

    @Column(length = 300)
    private String address;

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate eventDate;

    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime eventTime;      // start time

    private LocalDateTime gateOpenTime;     // entry start time

    private LocalDateTime eventEndTime;    // end time

    private String imageUrl;

    @Column(length = 100)
    private String artistName;

    @Column(length = 100)
    private String organizerName;

    private Integer minAge;

    @Column(length = 100)
    private String dressCode;

    @Column(nullable = false, length = 30)
    private String status;     // UPCOMING, LIVE, COMPLETED, CANCELLED

    @Column(nullable = false)
    private Boolean featured;    // home page highlight

    @Column(nullable = false)
    private Boolean trending;


    @Column(length = 300)
    private String tags;         // dj,vip,holi,party

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Transient
    public String getEventDay() {
        if (eventDate == null) {
            return null;
        }
        return eventDate.getDayOfWeek().toString();
    }

    @PrePersist
    public void prePersist() {
        if (status == null || status.isBlank()) {
            status = "UPCOMING";
        }

        if (featured == null) {
            featured = false;
        }

        if (trending == null) {
            trending = false;
        }

        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    
 // getters and setters 
	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public LocalTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(LocalTime eventTime) {
		this.eventTime = eventTime;
	}

	public LocalDateTime getGateOpenTime() {
		return gateOpenTime;
	}

	public void setGateOpenTime(LocalDateTime gateOpenTime) {
		this.gateOpenTime = gateOpenTime;
	}

	public LocalDateTime getEventEndTime() {
		return eventEndTime;
	}

	public void setEventEndTime(LocalDateTime eventEndTime) {
		this.eventEndTime = eventEndTime;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public String getDressCode() {
		return dressCode;
	}

	public void setDressCode(String dressCode) {
		this.dressCode = dressCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getFeatured() {
		return featured;
	}

	public void setFeatured(Boolean featured) {
		this.featured = featured;
	}

	public Boolean getTrending() {
		return trending;
	}

	public void setTrending(Boolean trending) {
		this.trending = trending;
	}


	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

    
    
    
}