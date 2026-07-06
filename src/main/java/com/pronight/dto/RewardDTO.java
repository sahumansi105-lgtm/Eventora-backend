package com.pronight.dto;

import java.util.List;

import com.pronight.entity.ClaimedRewardEntity;
import com.pronight.entity.RewardEntity;

public class RewardDTO {

    private Integer loyaltyPoints;

    private String membershipLevel;

    private Integer nextLevelPoints;

    private Integer remainingPoints;

    private List<RewardEntity> availableRewards;

    private List<ClaimedRewardEntity> claimedRewards;

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getMembershipLevel() {
        return membershipLevel;
    }

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public Integer getNextLevelPoints() {
        return nextLevelPoints;
    }

    public void setNextLevelPoints(Integer nextLevelPoints) {
        this.nextLevelPoints = nextLevelPoints;
    }

    public Integer getRemainingPoints() {
        return remainingPoints;
    }

    public void setRemainingPoints(Integer remainingPoints) {
        this.remainingPoints = remainingPoints;
    }

    public List<RewardEntity> getAvailableRewards() {
        return availableRewards;
    }

    public void setAvailableRewards(List<RewardEntity> availableRewards) {
        this.availableRewards = availableRewards;
    }

    public List<ClaimedRewardEntity> getClaimedRewards() {
        return claimedRewards;
    }

    public void setClaimedRewards(List<ClaimedRewardEntity> claimedRewards) {
        this.claimedRewards = claimedRewards;
    }
}
