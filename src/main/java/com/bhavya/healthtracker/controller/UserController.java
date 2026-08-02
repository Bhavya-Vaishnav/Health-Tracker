package com.bhavya.healthtracker.controller;

import com.bhavya.healthtracker.dto.userDTOs.UserResponseDTO;
import com.bhavya.healthtracker.dto.userDTOs.UserUpdateDTO;
import com.bhavya.healthtracker.dto.weeklyinsightDTO.WeeklyInsightResponseDTO;
import com.bhavya.healthtracker.service.UserService;
import com.bhavya.healthtracker.service.WeeklyInsightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final WeeklyInsightService weeklyInsightService;

    @GetMapping("/profile")
    public UserResponseDTO getUser() {
        String email = getEmail();

        return userService.getUser(email);
    }

    @PutMapping("/profile")
    public UserResponseDTO updateUser(@RequestBody UserUpdateDTO dto) {
        String email = getEmail();
        return userService.updateUser(email,dto);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, String> payload) {
        String email = getEmail();
        userService.deleteUser(email,payload);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/weekly-insight/current")
    public ResponseEntity<?> getCurrentWeekInsight() {
        String email = getEmail();
        WeeklyInsightResponseDTO dto = weeklyInsightService.generateCurrentWeekInsight(email);
        return ResponseEntity.ok(dto);
    }

    private String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
