package com.bhavya.healthtracker.controller;

import com.bhavya.healthtracker.dto.healthlogDTOs.HealthLogRequestDTO;
import com.bhavya.healthtracker.dto.healthlogDTOs.HealthLogResponseDTO;
import com.bhavya.healthtracker.dto.healthlogDTOs.HealthLogUpdateDTO;
import com.bhavya.healthtracker.service.HealthLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/health-log")
public class HealthlogController {

    @Autowired
    private HealthLogService healthLogService;

    @PostMapping
    public ResponseEntity<HealthLogResponseDTO> createLog(@Valid @RequestBody HealthLogRequestDTO dto) {
        String email = getEmail();
        return ResponseEntity.status(HttpStatus.CREATED).body(healthLogService.createLog(dto, email));
    }

    @GetMapping
    public ResponseEntity<List<HealthLogResponseDTO>> getAllLogs() {
        String email = getEmail();
        return ResponseEntity.ok(healthLogService.getAllLogs(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthLogResponseDTO> getLogById(@PathVariable String id) {
        String email = getEmail();
        return ResponseEntity.ok(healthLogService.getLogById(email, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthLogResponseDTO> updateById(@RequestBody HealthLogUpdateDTO dto, @PathVariable String id) {
        String email = getEmail();
        return ResponseEntity.ok(healthLogService.updateById(dto, email, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        String email = getEmail();
        healthLogService.deleteById(email, id);
        return ResponseEntity.noContent().build();
    }

    private String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}