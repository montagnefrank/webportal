package com.web.portal.repositories;

import com.web.portal.entities.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    // Additional query methods can be added here if needed
}
