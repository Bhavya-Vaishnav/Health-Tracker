package com.bhavya.healthtracker.service;

import com.bhavya.healthtracker.dto.healthlogDTOs.HealthLogRequestDTO;
import com.bhavya.healthtracker.dto.healthlogDTOs.HealthLogResponseDTO;
import com.bhavya.healthtracker.dto.healthlogDTOs.HealthLogUpdateDTO;
import com.bhavya.healthtracker.entity.HealthLog;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.exception.ResourceNotFoundException;
import com.bhavya.healthtracker.exception.UnauthorizedAccessException;
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

    public HealthLogResponseDTO createLog(HealthLogRequestDTO dto, String email) {
        User user = getCurrentUser(email);
        HealthLog healthLog = HealthLog.builder()
                .userId(user.getId())
                .date(dto.getDate())
                .exerciseMinutes(dto.getExerciseMinutes())
                .caloriesIntake(dto.getCaloriesIntake())
                .waterMl(dto.getWaterMl())
                .sleepHours(dto.getSleepHours())
                .notes(dto.getNotes())
                .build();
        healthLogRepository.save(healthLog);
        return toDto(healthLog);
    }

    public List<HealthLogResponseDTO> getAllLogs(String email) {
        User user = getCurrentUser(email);
        return healthLogRepository.findByUserId(user.getId())
                .stream().map(this::toDto).toList();
    }

    public HealthLogResponseDTO getLogById(String email, String id) {
        User user = getCurrentUser(email);
        HealthLog healthlog = healthLogRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("HealthLog not found with id: " + id));
        if (!healthlog.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You don't have access to this log");
        }
        return toDto(healthlog);
    }

    public HealthLogResponseDTO updateById(HealthLogUpdateDTO dto, String email, String id) {
        User user = getCurrentUser(email);
        HealthLog healthlog = healthLogRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("HealthLog not found with id: " + id));
        if (!healthlog.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You don't have access to this log");
        }
        if (dto.getDate() != null) healthlog.setDate(dto.getDate());
        if (dto.getExerciseMinutes() != null) healthlog.setExerciseMinutes(dto.getExerciseMinutes());
        if (dto.getCaloriesIntake() != null) healthlog.setCaloriesIntake(dto.getCaloriesIntake());
        if (dto.getWaterMl() != null) healthlog.setWaterMl(dto.getWaterMl());
        if (dto.getSleepHours() != null) healthlog.setSleepHours(dto.getSleepHours());
        if (dto.getNotes() != null) healthlog.setNotes(dto.getNotes());

        healthLogRepository.save(healthlog);
        return toDto(healthlog);
    }

    public void deleteById(String email, String id) {
        User user = getCurrentUser(email);
        HealthLog healthlog = healthLogRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("HealthLog not found with id: " + id));
        if (!healthlog.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You don't have access to this log");
        }
        healthLogRepository.delete(healthlog);
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
