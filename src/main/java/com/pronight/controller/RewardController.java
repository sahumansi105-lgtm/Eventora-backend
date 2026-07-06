package com.pronight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pronight.dto.RewardDTO;
import com.pronight.service.RewardService;

@RestController
@RequestMapping("/rewards")
@CrossOrigin(origins = "*")
public class RewardController {

    @Autowired
    private RewardService rewardService;

    @GetMapping
    public RewardDTO getRewards() {

        return rewardService.getRewards();

    }
    
    @PostMapping("/claim/{rewardId}")
    public String claimReward(@PathVariable Long rewardId) {

        rewardService.claimReward(rewardId);

        return "Reward claimed successfully";
    }

}