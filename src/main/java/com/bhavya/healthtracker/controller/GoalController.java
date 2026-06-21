package com.bhavya.healthtracker.controller;


import com.bhavya.healthtracker.dto.*;
import com.bhavya.healthtracker.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @PostMapping
    public ResponseEntity<?> createLog(@RequestBody GoalRequestDTO goalRequestDTO){
        String email = getEmail();
        return goalService.createGoal(goalRequestDTO,email);
    }

    @GetMapping
    public ResponseEntity<List<GoalResponseDTO>> getAllLogs(){
        String email = getEmail();
        return goalService.getAllLogs(email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> getLogById(@PathVariable String id){
        String email = getEmail();
        return goalService.getLogById(email,id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> updateById(@RequestBody GoalUpdateDTO dto, @PathVariable String id){
        String email = getEmail();
        return goalService.updateById(dto,email,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GoalResponseDTO> deleteById( @PathVariable String id){
        String email = getEmail();
        return goalService.deleteById(email,id);
    }

    private String getEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
