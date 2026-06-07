package com.bhavya.healthtracker.service;

import com.bhavya.healthtracker.dto.HealthLogRequestDTO;
import com.bhavya.healthtracker.dto.HealthLogResponseDTO;
import com.bhavya.healthtracker.dto.HealthLogUpdateDTO;
import com.bhavya.healthtracker.entity.HealthLog;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.repository.HealthLogRepository;
import com.bhavya.healthtracker.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HealthLogService {

    @Autowired
    private UserService userService;
    @Autowired
    private HealthLogRepository healthLogRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createLog(HealthLogRequestDTO dto, String email) {
        User user = getCurrentUser(email);
        HealthLog healthLog = HealthLog.builder().userId(user.getId()).date(dto.getDate()).exerciseMinutes(dto.getExerciseMinutes()).caloriesIntake(dto.getCaloriesIntake()).waterMl(dto.getWaterMl()).sleepHours(dto.getSleepHours()).notes(dto.getNotes()).build();
        healthLogRepository.save(healthLog);
        HealthLogResponseDTO response = toDto(healthLog);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    public ResponseEntity<List<HealthLogResponseDTO>> getAllLogs(String email) {
        try {
            User user = getCurrentUser(email);
            ObjectId userId = user.getId();
            List<HealthLog> logs = healthLogRepository.findByUserId(userId);
            List<HealthLogResponseDTO> response =
                    logs.stream()
                            .map(this::toDto)
                            .toList();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred during getting health logs:", e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public List<HealthLogResponseDTO> getLogsForUser(ObjectId userId) {
        return healthLogRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ResponseEntity<HealthLogResponseDTO> getLogById(String email, String id) {
        User user = getCurrentUser(email);
        HealthLog healthlog = healthLogRepository
                .findById(new ObjectId(id))
                .orElse(null);
        if (healthlog != null) {
            if (!healthlog.getUserId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<>(toDto(healthlog), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<HealthLogResponseDTO> updateById(HealthLogUpdateDTO dto, String email, String id) {
        User user = getCurrentUser(email);
        HealthLog healthlog = healthLogRepository
                .findById(new ObjectId(id))
                .orElse(null);
        if (!healthlog.getUserId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            if (dto.getDate() != null) {
                healthlog.setDate(dto.getDate());
            }
            if (dto.getExerciseMinutes() != null) {
                healthlog.setExerciseMinutes(dto.getExerciseMinutes());
            }
            if (dto.getCaloriesIntake() != null) {
                healthlog.setCaloriesIntake(dto.getCaloriesIntake());
            }
            if (dto.getWaterMl() != null) {
                healthlog.setWaterMl(dto.getWaterMl());
            }
            if (dto.getSleepHours() != null) {
                healthlog.setSleepHours(dto.getSleepHours());
            }
            if (dto.getNotes() != null) {
                healthlog.setNotes(dto.getNotes());
            }
        }
        healthLogRepository.save(healthlog);
        return new ResponseEntity<>(toDto(healthlog), HttpStatus.OK);
    }

    public ResponseEntity<HealthLogResponseDTO> deleteById(String email, String id) {
        User user = getCurrentUser(email);
        HealthLog healthlog = healthLogRepository
                .findById(new ObjectId(id))
                .orElse(null);
        if (healthlog != null) {
            if (!healthlog.getUserId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                healthLogRepository.delete(healthlog);
                return ResponseEntity.noContent().build();
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private User getCurrentUser(String email) {
        return userService.findByEmail(email);
    }

    private HealthLogResponseDTO toDto(HealthLog log) {
        return HealthLogResponseDTO.builder()
                .id(log.getId().toHexString())
                .date(log.getDate())
                .exerciseMinutes(log.getExerciseMinutes())
                .caloriesIntake(log.getCaloriesIntake())
                .waterMl(log.getWaterMl())
                .sleepHours(log.getSleepHours())
                .notes(log.getNotes())
                .build();
    }
}
