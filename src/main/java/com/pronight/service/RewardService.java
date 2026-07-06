package com.pronight.service;

import com.pronight.dto.RewardDTO;

public interface RewardService {

    RewardDTO getRewards();

	void claimReward(Long rewardId);

}