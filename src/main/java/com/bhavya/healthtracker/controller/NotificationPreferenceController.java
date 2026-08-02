package com.bhavya.healthtracker.controller;

import com.bhavya.healthtracker.dto.notificationpreferenceDTOs.NotificationPreferenceResponseDTO;
import com.bhavya.healthtracker.dto.notificationpreferenceDTOs.NotificationPreferenceUpdateDTO;
import com.bhavya.healthtracker.service.NotificationPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/notification-preference")
public class NotificationPreferenceController {

    private final NotificationPreferenceService notificationPreferenceService;

    @GetMapping
    public ResponseEntity<NotificationPreferenceResponseDTO> getOrCreate() {
        String email = getEmail();
        return ResponseEntity.ok(notificationPreferenceService.getOrCreate(email));
    }

    @PutMapping
    public ResponseEntity<NotificationPreferenceResponseDTO> togglePreference(@RequestBody NotificationPreferenceUpdateDTO dto) {
        String email = getEmail();
        return ResponseEntity.ok(notificationPreferenceService.togglePreference(email, dto));
    }

    private String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}