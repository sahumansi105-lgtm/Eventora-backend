package com.pronight.repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pronight.entity.PassEntity;

@Repository
public interface PassRepo extends JpaRepository<PassEntity, Long> {

    // ===== BASIC =====
    List<PassEntity> findAll();

    // ===== FILTERS (NO activeTrue) =====
    List<PassEntity> findByEventEventId(Long eventId);

    List<PassEntity> findByPassTypeIgnoreCase(String passType);

    List<PassEntity> findByStatus(String status);

    List<PassEntity> findByAvailableQuantityLessThanEqual(Integer quantity);

    // Locks the pass row during payment verification so two users cannot buy the last pass at the same time.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from PassEntity p where p.passId = :passId")
    Optional<PassEntity> findByIdForUpdate(@Param("passId") Long passId);
}
