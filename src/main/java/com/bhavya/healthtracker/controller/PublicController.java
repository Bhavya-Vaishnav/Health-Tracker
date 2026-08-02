package com.bhavya.healthtracker.controller;

import com.bhavya.healthtracker.dto.userDTOs.LoginDTO;
import com.bhavya.healthtracker.dto.userDTOs.UserRequestDTO;
import com.bhavya.healthtracker.dto.userDTOs.UserResponseDTO;
import com.bhavya.healthtracker.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PublicController {

    private final UserService userService;

    @GetMapping("/health-check")
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDTO> signup(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO created = userService.saveNewUser(userRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.authenticate(loginDTO));
    }
}