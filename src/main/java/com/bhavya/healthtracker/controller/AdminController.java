package com.bhavya.healthtracker.controller;


import com.bhavya.healthtracker.dto.userDTOs.UserResponseDTO;
import com.bhavya.healthtracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsersAdmin());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserByIdAdmin(id));
    }

    @PutMapping("/users/{id}/disable")
    public ResponseEntity<Void> disableUser(@PathVariable String id) {
        userService.disableUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUserAdmin(id);
        return ResponseEntity.noContent().build();
    }
}
