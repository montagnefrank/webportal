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

    @GetMapping
    public List<Movement> getAllMovements() {
        return movementService.getAllMovements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movement> getMovementById(@PathVariable Long id) {
        Optional<Movement> movement = movementService.getMovementById(id);
        return movement.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Movement> createMovement(@RequestBody Movement movement) {
        Movement createdMovement = movementService.createMovement(movement);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovement);
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
