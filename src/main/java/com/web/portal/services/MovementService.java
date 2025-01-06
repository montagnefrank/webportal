package com.web.portal.services;

import com.web.portal.entities.Movement;
import com.web.portal.repositories.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementService {

    private final MovementRepository movementRepository;

    @Autowired
    public MovementService(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public List<Movement> getAllMovements() {
        return movementRepository.findAll();
    }

    public Optional<Movement> getMovementById(Long id) {
        return movementRepository.findById(id);
    }

    public Movement createMovement(Movement movement) {
        return movementRepository.save(movement);
    }

    public void deleteMovement(Long id) {
        movementRepository.deleteById(id);
    }

    public Movement updateMovement(Long id, Movement movementDetails) {
        Movement movement = movementRepository.findById(id).orElseThrow();
        movement.setCustomerId(movementDetails.getCustomerId());
        movement.setType(movementDetails.getType());
        movement.setAmount(movementDetails.getAmount());
        movement.setDate(movementDetails.getDate());
        return movementRepository.save(movement);
    }
}
