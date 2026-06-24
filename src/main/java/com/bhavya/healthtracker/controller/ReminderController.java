package com.bhavya.healthtracker.controller;

import com.bhavya.healthtracker.dto.reminderDTOs.ReminderRequestDTO;
import com.bhavya.healthtracker.dto.reminderDTOs.ReminderResponseDTO;
import com.bhavya.healthtracker.dto.reminderDTOs.ReminderUpdateDTO;
import com.bhavya.healthtracker.service.ReminderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/reminder")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @PostMapping
    public ResponseEntity<ReminderResponseDTO> createReminder(@Valid @RequestBody ReminderRequestDTO dto) {
        String email = getEmail();
        return ResponseEntity.status(HttpStatus.CREATED).body(reminderService.createReminder(dto, email));
    }

    @GetMapping
    public ResponseEntity<List<ReminderResponseDTO>> getAllReminders() {
        String email = getEmail();
        return ResponseEntity.ok(reminderService.getAllReminders(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderResponseDTO> getReminderById(@PathVariable String id) {
        String email = getEmail();
        return ResponseEntity.ok(reminderService.getReminderById(email, id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderResponseDTO> updateById(@RequestBody ReminderUpdateDTO dto, @PathVariable String id) {
        String email = getEmail();
        return ResponseEntity.ok(reminderService.updateById(dto, email, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        String email = getEmail();
        reminderService.deleteById(email, id);
        return ResponseEntity.noContent().build();
    }

    private String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}