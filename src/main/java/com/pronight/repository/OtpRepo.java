package com.pronight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pronight.entity.OtpEntity;

public interface OtpRepo extends JpaRepository<OtpEntity, Long> {

    OtpEntity findTopByEmailOrderByIdDesc(String email);

}