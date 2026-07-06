package com.pronight.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pronight.entity.RewardEntity;

public interface RewardRepo
        extends JpaRepository<RewardEntity, Long> {

}