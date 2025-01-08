package com.web.portal.controllers;

import com.web.portal.entities.Movement;
import com.web.portal.services.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovementsController {

    private final MovementService movementService;

    @Autowired
    public MovementsController(MovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping
    public ResponseEntity<?> createMovement(@RequestBody Movement movement) {
        try {
            Movement createdMovement = movementService.createMovement(movement);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdMovement);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Movement>> getAllMovements() {
        List<Movement> movements = movementService.getAllMovements();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/cuenta/{accountNumber}")
    public ResponseEntity<List<Movement>> getMovementsByAccountNumber(@PathVariable String accountNumber) {
        List<Movement> movements = movementService.getMovementsByAccountNumber(accountNumber);
        return movements != null && !movements.isEmpty() ? ResponseEntity.ok(movements) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movement> getMovementById(@PathVariable Long id) {
        Optional<Movement> movement = movementService.getMovementById(id);
        return movement.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movement> updateMovement(@PathVariable Long id, @RequestBody Movement movementDetails) {
        Movement updatedMovement = movementService.updateMovement(id, movementDetails);
        return ResponseEntity.ok(updatedMovement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long id) {
        movementService.deleteMovement(id);
        return ResponseEntity.noContent().build();
    }
}
