package com.pronight.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pronight.entity.EventEntity;

public interface EventRepo extends JpaRepository<EventEntity, Long> {

    List<EventEntity> findByStatus(String status);

    List<EventEntity> findBySeasonIgnoreCase(String season);

    List<EventEntity> findByCityIgnoreCase(String city);

    List<EventEntity> findByCategoryIgnoreCase(String category);

    List<EventEntity> findByMoodIgnoreCase(String mood);

    List<EventEntity> findByFeaturedTrue();

    List<EventEntity> findByTrendingTrue();

    List<EventEntity> findByEventDateGreaterThanEqualOrderByEventDateAscEventTimeAsc(LocalDate date);

    List<EventEntity> findByTitleContainingIgnoreCase(String keyword);
}
