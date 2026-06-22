package com.bhavya.healthtracker.controller;

import com.bhavya.healthtracker.dto.*;
import com.bhavya.healthtracker.entity.Reminder;
import com.bhavya.healthtracker.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> createLog(@RequestBody ReminderRequestDTO reminderRequestDTO){
        String email = getEmail();
        return reminderService.createReminder(reminderRequestDTO,email);
    }

    @GetMapping
    public ResponseEntity<List<ReminderResponseDTO>> getAllReminders(){
        String email = getEmail();
        return reminderService.getAllLogs(email);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderResponseDTO> getReminderById(@PathVariable String id){
        String email = getEmail();
        return reminderService.getLogById(email,id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderResponseDTO> updateById(@RequestBody ReminderUpdateDTO dto, @PathVariable String id){
        String email = getEmail();
        return reminderService.updateById(dto,email,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReminderResponseDTO> deleteById( @PathVariable String id){
        String email = getEmail();
        return reminderService.deleteById(email,id);
    }

    private String getEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
