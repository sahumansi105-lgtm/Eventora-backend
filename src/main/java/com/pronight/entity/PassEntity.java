package com.pronight.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "passes")
public class PassEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long passId;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(nullable = false, length = 50)
    private String passType; // SILVER, GOLD, VIP, VVIP, COUPLE

    @Column(nullable = false, length = 100)
    private String passName;         // "VIP Fan Zone", "Gold Entry Pass"

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Double price;

    private Double discountPrice;   // optional offer price

    @Column(nullable = false, length = 10)
    private String currency;         //INR

    @Column(nullable = false)
    private Integer totalQuantity;

    @Column(nullable = false)
    private Integer availableQuantity;

    @Column(nullable = false)
    private Integer soldQuantity;

    @Column(nullable = false)
    private Integer minPerBooking;      // minimum 1

    @Column(nullable = false)
    private Integer maxPerBooking;       // ek booking me max kitne pass

    @Column(nullable = false)
    private Integer maxPerUser;   // ek user total max kitne pass le sakta hai


    @Column(length = 100)
    private String entryGate;    // Gate A, Gate B, VIP Gate

    @Column(length = 50)
    private String seatingType; // STANDING, SEATED, FRONT_ROW, LOUNGE


    @Column(length = 50)
    private String highlightLabel; // Best Seller, Limited, Early Bird

    private LocalDateTime saleStartDateTime;

    private LocalDateTime saleEndDateTime;

    @Column(nullable = false, length = 30)
    private String status; // AVAILABLE, SOLD_OUT, PAUSED, EXPIRED

  
    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (currency == null || currency.isBlank()) {
            currency = "INR";
        }

        if (soldQuantity == null) {
            soldQuantity = 0;
        }

        if (availableQuantity == null && totalQuantity != null) {
            availableQuantity = totalQuantity;
        }

        if (minPerBooking == null) {
            minPerBooking = 1;
        }

        if (maxPerBooking == null) {
            maxPerBooking = 10;
        }

        if (maxPerUser == null) {
            maxPerUser = 10;
        }


        if (status == null || status.isBlank()) {
            status = "AVAILABLE";
        }

        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();

        if (availableQuantity != null && availableQuantity <= 0) {
            status = "SOLD_OUT";
        }
    }

    
    private Double basePrice;

    private Integer firstTierLimit;
    private Double firstTierPrice;

    private Integer secondTierLimit;
    private Double secondTierPrice;

    private Double finalTierPrice;
    
    @Column(length = 500)
    private String foodBenefits;

    
 // getters and setters 

	public Long getPassId() {
		return passId;
	}

	public void setPassId(Long passId) {
		this.passId = passId;
	}

	public EventEntity getEvent() {
		return event;
	}

	public void setEvent(EventEntity event) {
		this.event = event;
	}

	public String getPassType() {
		return passType;
	}

	public void setPassType(String passType) {
		this.passType = passType;
	}

	public String getPassName() {
		return passName;
	}

	public void setPassName(String passName) {
		this.passName = passName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Integer getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Integer totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public Integer getSoldQuantity() {
		return soldQuantity;
	}

	public void setSoldQuantity(Integer soldQuantity) {
		this.soldQuantity = soldQuantity;
	}

	public Integer getMinPerBooking() {
		return minPerBooking;
	}

	public void setMinPerBooking(Integer minPerBooking) {
		this.minPerBooking = minPerBooking;
	}

	public Integer getMaxPerBooking() {
		return maxPerBooking;
	}

	public void setMaxPerBooking(Integer maxPerBooking) {
		this.maxPerBooking = maxPerBooking;
	}

	public Integer getMaxPerUser() {
		return maxPerUser;
	}

	public void setMaxPerUser(Integer maxPerUser) {
		this.maxPerUser = maxPerUser;
	}


	public String getEntryGate() {
		return entryGate;
	}

	public void setEntryGate(String entryGate) {
		this.entryGate = entryGate;
	}

	public String getSeatingType() {
		return seatingType;
	}

	public void setSeatingType(String seatingType) {
		this.seatingType = seatingType;
	}

	public String getHighlightLabel() {
		return highlightLabel;
	}

	public void setHighlightLabel(String highlightLabel) {
		this.highlightLabel = highlightLabel;
	}

	public LocalDateTime getSaleStartDateTime() {
		return saleStartDateTime;
	}

	public void setSaleStartDateTime(LocalDateTime saleStartDateTime) {
		this.saleStartDateTime = saleStartDateTime;
	}

	public LocalDateTime getSaleEndDateTime() {
		return saleEndDateTime;
	}

	public void setSaleEndDateTime(LocalDateTime saleEndDateTime) {
		this.saleEndDateTime = saleEndDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	public Double getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Integer getFirstTierLimit() {
		return firstTierLimit;
	}

	public void setFirstTierLimit(Integer firstTierLimit) {
		this.firstTierLimit = firstTierLimit;
	}

	public Double getFirstTierPrice() {
		return firstTierPrice;
	}

	public void setFirstTierPrice(Double firstTierPrice) {
		this.firstTierPrice = firstTierPrice;
	}

	public Integer getSecondTierLimit() {
		return secondTierLimit;
	}

	public void setSecondTierLimit(Integer secondTierLimit) {
		this.secondTierLimit = secondTierLimit;
	}

	public Double getSecondTierPrice() {
		return secondTierPrice;
	}

	public void setSecondTierPrice(Double secondTierPrice) {
		this.secondTierPrice = secondTierPrice;
	}

	public Double getFinalTierPrice() {
		return finalTierPrice;
	}

	public void setFinalTierPrice(Double finalTierPrice) {
		this.finalTierPrice = finalTierPrice;
	}

	public String getFoodBenefits() {
		return foodBenefits;
	}

	public void setFoodBenefits(String foodBenefits) {
		this.foodBenefits = foodBenefits;
	}

    
    
}