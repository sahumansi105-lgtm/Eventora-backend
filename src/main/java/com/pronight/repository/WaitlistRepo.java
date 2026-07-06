package com.pronight.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pronight.entity.PassEntity;
import com.pronight.entity.WaitlistEntity;

public interface WaitlistRepo extends JpaRepository<WaitlistEntity, Long>{

    long countByPass(PassEntity pass);


	List<WaitlistEntity> findByPassAndStatusOrderByWaitlistPositionAsc(
			PassEntity pass, 
			String status);

}