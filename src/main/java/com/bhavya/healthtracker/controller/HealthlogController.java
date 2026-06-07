package com.bhavya.healthtracker.controller;


import com.bhavya.healthtracker.dto.HealthLogRequestDTO;
import com.bhavya.healthtracker.dto.HealthLogResponseDTO;
import com.bhavya.healthtracker.dto.HealthLogUpdateDTO;
import com.bhavya.healthtracker.service.HealthLogService;
import com.bhavya.healthtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/health-log")
public class HealthlogController {

    @Autowired
    private UserService userService;
    @Autowired
    private HealthLogService healthLogService;

    @PostMapping
    public ResponseEntity<?> createLog(@RequestBody HealthLogRequestDTO healthLogRequestDTO){
        String email = getEmail();
        return healthLogService.createLog(healthLogRequestDTO,email);
    }

    @GetMapping
    public ResponseEntity<List<HealthLogResponseDTO>> getAllLogs(){
        String email = getEmail();
        return healthLogService.getAllLogs(email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthLogResponseDTO> getLogById(@PathVariable String id){
        String email = getEmail();
        return healthLogService.getLogById(email,id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthLogResponseDTO> updateById(@RequestBody HealthLogUpdateDTO dto, @PathVariable String id){
        String email = getEmail();
        return healthLogService.updateById(dto,email,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HealthLogResponseDTO> deleteById( @PathVariable String id){
        String email = getEmail();
        return healthLogService.deleteById(email,id);
    }

    private String getEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
