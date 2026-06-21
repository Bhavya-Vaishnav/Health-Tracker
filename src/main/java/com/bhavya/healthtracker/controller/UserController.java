package com.bhavya.healthtracker.controller;

import com.bhavya.healthtracker.dto.HealthLogResponseDTO;
import com.bhavya.healthtracker.dto.UserResponseDTO;
import com.bhavya.healthtracker.dto.UserUpdateDTO;
import com.bhavya.healthtracker.dto.WeeklyInsightResponseDTO;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.entity.WeeklyInsight;
import com.bhavya.healthtracker.service.HealthLogService;
import com.bhavya.healthtracker.service.UserService;
import com.bhavya.healthtracker.service.WeeklyInsightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private HealthLogService healthLogService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WeeklyInsightService weeklyInsightService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getUser() {

        String email = getEmail();

        User user = userService.findByEmail(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        List<HealthLogResponseDTO> logs =
                healthLogService.getLogsForUser(user.getId());

        UserResponseDTO dto = new UserResponseDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setHealthLogs(logs);

        return ResponseEntity.ok(dto);
    }

    @PutMapping("/profile")
    public ResponseEntity<String> updateUser(@RequestBody UserUpdateDTO request) {
        String email = getEmail();

        User userInDb = userService.findByEmail(email);

        if (userInDb != null) {
            if (request.getPassword() != null && !request.getPassword().isEmpty()) {
                userInDb.setPassword(passwordEncoder.encode(request.getPassword()));
            }
            if (request.getEnabled() != null) {
                userInDb.setEnabled(request.getEnabled());
            }
            userService.updateUser(userInDb);

            return ResponseEntity.ok("User updated successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody Map<String, String> payload) {
        String email = getEmail();
        String rawPassword = payload.get("password");
        User user = userService.findByEmail(email);
        if (user != null && passwordEncoder.matches(rawPassword, user.getPassword())) {
            userService.deleteUser(user);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/weekly-insight/current")
    public ResponseEntity<?> getCurrentWeekInsight() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        WeeklyInsightResponseDTO dto = weeklyInsightService.generateCurrentWeekInsight(email);
        return ResponseEntity.ok(dto);
    }

    private String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
