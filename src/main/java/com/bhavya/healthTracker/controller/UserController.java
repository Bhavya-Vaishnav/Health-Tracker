package com.bhavya.healthTracker.controller;

import com.bhavya.healthTracker.entity.User;
import com.bhavya.healthTracker.service.UserService;
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
        String name = authentication.getName();
        User user = userService.findByName(name);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User userInDb = userService.findByName(user.getName());
        if (userInDb != null) {
            if (!user.getPasswordHash().isEmpty()) {
                userInDb.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
            }
            if (!user.getPasswordHash().isEmpty()) {
                userInDb.setEmail(user.getEmail());
            }
            if (!user.getRoles().isEmpty()) {
                userInDb.setRoles(user.getRoles());
            }
            userInDb.setEnabled(user.isEnabled());
            User updatedUser = userService.updateUser(userInDb);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, String> payload) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String rawPassword = payload.get("password");
        User user = userService.findByName(username);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            userService.deleteUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
