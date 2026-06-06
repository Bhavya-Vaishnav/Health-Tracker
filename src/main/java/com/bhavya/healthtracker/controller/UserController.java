package com.bhavya.healthtracker.controller;

import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.dto.UserResponseDTO;
import com.bhavya.healthtracker.dto.UserUpdateDTO;
import com.bhavya.healthtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public ResponseEntity<?> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        String email = authentication.getName();
        User user = userService.findByEmail(email);

        if (user != null) {
            UserResponseDTO dto = new UserResponseDTO();
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setEnabled(user.isEnabled());

            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        String email = authentication.getName();

        User userInDb = userService.findByEmail(email);

        if (userInDb != null) {
            if (request.getEmail() != null && !request.getEmail().isEmpty()) {
                userInDb.setEmail(request.getEmail());
            }

            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                userInDb.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            if (request.getEnabled() != null) {
                userInDb.setEnabled(request.getEnabled());
            }
            User updatedUser = userService.updateUser(userInDb);

            return ResponseEntity.ok("User updated successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, String> payload) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        String rawPassword = payload.get("password");
        User user = userService.findByEmail(email);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            userService.deleteUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
