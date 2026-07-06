package com.pronight.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pronight.entity.EventEntity;
import com.pronight.entity.UserEntity;
import com.pronight.entity.WishlistEntity;

public interface WishlistRepo extends JpaRepository<WishlistEntity, Long> {

    List<WishlistEntity> findByUser(UserEntity user);

    Optional<WishlistEntity> findByUserAndEvent(
            UserEntity user,
            EventEntity event);
}