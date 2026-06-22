package com.bhavya.healthtracker.controller;

import com.bhavya.healthtracker.dto.NotificationPreferenceResponseDTO;
import com.bhavya.healthtracker.dto.NotificationPreferenceUpdateDTO;
import com.bhavya.healthtracker.entity.NotificationPreference;
import com.bhavya.healthtracker.service.NotificationPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/notification-preference")
public class NotificationPreferenceController {

    @Autowired
    private NotificationPreferenceService notificationPreferenceService;

    @GetMapping
    public ResponseEntity<NotificationPreferenceResponseDTO> getOrcreate(){
        String email = getEmail();
        return notificationPreferenceService.getOrCreate(email);
    }

    @PostMapping
    public ResponseEntity<NotificationPreferenceResponseDTO> togglePreference(@RequestBody NotificationPreferenceUpdateDTO dto){
        String email = getEmail();
        return notificationPreferenceService.togglePreference(email,dto);
    }

    private String getEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
