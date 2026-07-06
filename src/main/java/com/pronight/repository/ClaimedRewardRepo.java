package com.pronight.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pronight.entity.ClaimedRewardEntity;

public interface ClaimedRewardRepo
        extends JpaRepository<ClaimedRewardEntity, Long> {

    List<ClaimedRewardEntity> findByUserUserIdOrderByClaimedAtDesc(Long userId);

    boolean existsByRewardCode(String rewardCode);
}
