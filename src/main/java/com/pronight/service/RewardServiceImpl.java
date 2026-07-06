package com.pronight.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pronight.dto.RewardDTO;
import com.pronight.entity.ClaimedRewardEntity;
import com.pronight.entity.RewardEntity;
import com.pronight.entity.UserEntity;
import com.pronight.repository.ClaimedRewardRepo;
import com.pronight.repository.RewardRepo;
import com.pronight.repository.UserRepo;

@Service
public class RewardServiceImpl implements RewardService {
	
	@Autowired
	private RewardRepo rewardRepo;

	@Autowired
	private ClaimedRewardRepo claimedRewardRepo;

    @Autowired
    private UserRepo userRepo;

    private UserEntity getLoggedInUser() {

        Authentication authentication =
                SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }
    
    
    @Override
    public void claimReward(Long rewardId) {

        UserEntity user = getLoggedInUser();

        RewardEntity reward = rewardRepo.findById(rewardId)
                .orElseThrow(() ->
                        new RuntimeException("Reward not found"));

        int userPoints = user.getLoyaltyPoints() == null ? 0 : user.getLoyaltyPoints();
        int requiredPoints = reward.getPointsRequired() == null ? 0 : reward.getPointsRequired();

        if (userPoints < requiredPoints) {
            throw new RuntimeException("Not enough loyalty points to claim this reward");
        }

        user.setLoyaltyPoints(userPoints - requiredPoints);
        updateMembershipLevel(user);
        userRepo.save(user);


        ClaimedRewardEntity claim = new ClaimedRewardEntity();

        claim.setUser(user);

        claim.setReward(reward);

        claim.setClaimedAt(LocalDateTime.now());

        // This is the code your frontend can show to the user for reward redemption.
        claim.setRewardCode(generateRewardCode(user.getUserId(), reward.getRewardId()));

        claim.setStatus("ACTIVE");

        claimedRewardRepo.save(claim);
    }


	@Override
	public RewardDTO getRewards() {
        UserEntity user = getLoggedInUser();
        int points = user.getLoyaltyPoints() == null ? 0 : user.getLoyaltyPoints();
        int nextLevelPoints = calculateNextLevelPoints(points);

        RewardDTO dto = new RewardDTO();
        dto.setLoyaltyPoints(points);
        dto.setMembershipLevel(user.getMembership_level());
        dto.setNextLevelPoints(nextLevelPoints);
        dto.setRemainingPoints(Math.max(0, nextLevelPoints - points));
        dto.setAvailableRewards(rewardRepo.findAll());
        dto.setClaimedRewards(
                claimedRewardRepo.findByUserUserIdOrderByClaimedAtDesc(user.getUserId())
        );

		return dto;
	}

    private String generateRewardCode(Long userId, Long rewardId) {
        String rewardCode;

        do {
            String randomPart = UUID.randomUUID()
                    .toString()
                    .replace("-", "")
                    .substring(0, 8)
                    .toUpperCase();

            rewardCode = "PNR-" + userId + "-" + rewardId + "-" + randomPart;
        } while (claimedRewardRepo.existsByRewardCode(rewardCode));

        return rewardCode;
    }

    private int calculateNextLevelPoints(int points) {
        if (points < 500) {
            return 500;
        }
        if (points < 1500) {
            return 1500;
        }
        if (points < 3000) {
            return 3000;
        }
        return points;
    }

    private void updateMembershipLevel(UserEntity user) {
        int points = user.getLoyaltyPoints() == null ? 0 : user.getLoyaltyPoints();

        if (points >= 3000) {
            user.setMembership_level("PLATINUM");
        } else if (points >= 1500) {
            user.setMembership_level("GOLD");
        } else if (points >= 500) {
            user.setMembership_level("SILVER");
        } else {
            user.setMembership_level("BRONZE");
        }
    }
}
