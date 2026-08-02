package com.bhavya.healthtracker.controller;

import com.bhavya.healthtracker.dto.goalDTOs.GoalRequestDTO;
import com.bhavya.healthtracker.dto.goalDTOs.GoalResponseDTO;
import com.bhavya.healthtracker.dto.goalDTOs.GoalUpdateDTO;
import com.bhavya.healthtracker.service.GoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/goals")
public class GoalController {

    private final GoalService goalService;

    @PostMapping
    public ResponseEntity<GoalResponseDTO> createGoal(@Valid @RequestBody GoalRequestDTO dto) {
        String email = getEmail();
        return ResponseEntity.status(HttpStatus.CREATED).body(goalService.createGoal(dto, email));
    }

    @GetMapping
    public ResponseEntity<List<GoalResponseDTO>> getAllGoals() {
        String email = getEmail();
        return ResponseEntity.ok(goalService.getAllGoals(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> getGoalById(@PathVariable String id) {
        String email = getEmail();
        return ResponseEntity.ok(goalService.getGoalById(email, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> updateById(@RequestBody GoalUpdateDTO dto, @PathVariable String id) {
        String email = getEmail();
        return ResponseEntity.ok(goalService.updateById(dto, email, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        String email = getEmail();
        goalService.deleteById(email, id);
        return ResponseEntity.noContent().build();
    }

    private String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}