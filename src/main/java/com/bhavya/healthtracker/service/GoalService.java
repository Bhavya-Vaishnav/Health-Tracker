package com.bhavya.healthtracker.service;

import com.bhavya.healthtracker.dto.GoalRequestDTO;
import com.bhavya.healthtracker.dto.GoalResponseDTO;
import com.bhavya.healthtracker.dto.GoalUpdateDTO;
import com.bhavya.healthtracker.entity.Goal;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.enums.GoalStatus;
import com.bhavya.healthtracker.repository.GoalRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GoalService {

    @Autowired
    private UserService userService;
    @Autowired
    private GoalRepository goalRepository;

    public ResponseEntity<?> createGoal(GoalRequestDTO dto, String email) {
        User user = getCurrentUser(email);
        Goal goal = Goal.builder().userId(user.getId()).goalType(dto.getGoalType()).targetValue(dto.getTargetValue()).currentValue(dto.getCurrentValue()).unit(dto.getUnit()).startDate(dto.getStartDate()).targetDate(dto.getTargetDate()).status(dto.getStatus()).build();
        goalRepository.save(goal);
        return new ResponseEntity<>(toDto(goal), HttpStatus.CREATED);
    }

    public ResponseEntity<List<GoalResponseDTO>> getAllLogs(String email) {
        try {
            User user = getCurrentUser(email);
            ObjectId userId = user.getId();
            List<Goal> logs = goalRepository.findByUserId(userId);
            List<GoalResponseDTO> response =
                    logs.stream()
                            .map(this::toDto)
                            .toList();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred during getting Goals:", e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<GoalResponseDTO> getLogById(String email, String id) {
        User user = getCurrentUser(email);
        Goal goal = goalRepository
                .findById(new ObjectId(id))
                .orElse(null);
        if (goal != null) {
            if (!goal.getUserId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<>(toDto(goal), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<GoalResponseDTO> updateById(
            GoalUpdateDTO dto,
            String email,
            String id) {
        User user = getCurrentUser(email);
        Goal goal = goalRepository
                .findById(new ObjectId(id))
                .orElse(null);
        if (goal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!goal.getUserId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (dto.getGoalType() != null)
            goal.setGoalType(dto.getGoalType());
        if (dto.getTargetValue() != null)
            goal.setTargetValue(dto.getTargetValue());
        if (dto.getCurrentValue() != null)
            goal.setCurrentValue(dto.getCurrentValue());
        if (dto.getUnit() != null)
            goal.setUnit(dto.getUnit());
        if (dto.getStartDate() != null)
            goal.setStartDate(dto.getStartDate());
        if (dto.getTargetDate() != null)
            goal.setTargetDate(dto.getTargetDate());
        if (dto.getStatus() != null)
            goal.setStatus(dto.getStatus());
        goalRepository.save(goal);
        return ResponseEntity.ok(toDto(goal));
    }

    public ResponseEntity<GoalResponseDTO> deleteById(String email, String id) {
        User user = getCurrentUser(email);
        Goal goal = goalRepository
                .findById(new ObjectId(id))
                .orElse(null);
        if (goal != null) {
            if (!goal.getUserId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                goalRepository.delete(goal);
                return ResponseEntity.noContent().build();
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private User getCurrentUser(String email) {
        return userService.findByEmail(email);
    }

    private GoalResponseDTO toDto(Goal goal) {
        return GoalResponseDTO.builder()
                .id(goal.getId().toHexString())
                .goalType(goal.getGoalType())
                .targetValue(goal.getTargetValue())
                .currentValue(goal.getCurrentValue())
                .unit(goal.getUnit())
                .startDate(goal.getStartDate())
                .targetDate(goal.getTargetDate())
                .status(goal.getStatus())
                .build();
    }

}
