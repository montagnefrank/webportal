package com.web.portal.repositories;

import com.web.portal.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long> {
    List<Movement> findByAccount_AccountNumberContainingIgnoreCase(String accountNumber); 
    List<Movement> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}