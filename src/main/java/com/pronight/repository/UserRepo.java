package com.pronight.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pronight.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByActiveTrue();

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    UserEntity findByEmailAndPassword(String email, String password);

}
