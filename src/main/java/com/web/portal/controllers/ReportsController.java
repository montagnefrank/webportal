package com.web.portal.controllers;

import com.web.portal.entities.Movement;
import com.web.portal.services.MovementService;
import com.web.portal.dto.MovementReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reportes")
public class ReportsController {

    private final MovementService movementService;

    @Autowired
    public ReportsController(MovementService movementService) {
        this.movementService = movementService;
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<MovementReportDTO>> getMovementReportsByDateRange(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        try {
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);
 
            LocalDateTime startDateTime = start.atStartOfDay();
            LocalDateTime endDateTime = end.atTime(23, 59, 59);

            List<MovementReportDTO> reports = movementService.getMovementReportsByDateRange(startDateTime, endDateTime);
            if (reports.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
