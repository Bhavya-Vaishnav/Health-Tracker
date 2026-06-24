package com.bhavya.healthtracker.service;

import com.bhavya.healthtracker.dto.goalDTOs.GoalRequestDTO;
import com.bhavya.healthtracker.dto.goalDTOs.GoalResponseDTO;
import com.bhavya.healthtracker.dto.goalDTOs.GoalUpdateDTO;
import com.bhavya.healthtracker.entity.Goal;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.exception.ResourceNotFoundException;
import com.bhavya.healthtracker.exception.UnauthorizedAccessException;
import com.bhavya.healthtracker.repository.GoalRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GoalService {

    @Autowired
    private UserService userService;
    @Autowired
    private GoalRepository goalRepository;

    public GoalResponseDTO createGoal(GoalRequestDTO dto, String email) {
        User user = getCurrentUser(email);
        Goal goal = Goal.builder()
                .userId(user.getId())
                .goalType(dto.getGoalType())
                .targetValue(dto.getTargetValue())
                .currentValue(dto.getCurrentValue())
                .unit(dto.getUnit())
                .startDate(dto.getStartDate())
                .targetDate(dto.getTargetDate())
                .status(dto.getStatus())
                .build();
        goalRepository.save(goal);
        return toDto(goal);
    }

    public List<GoalResponseDTO> getAllGoals(String email) {
        User user = getCurrentUser(email);
        return goalRepository.findByUserId(user.getId())
                .stream().map(this::toDto).toList();
    }

    public GoalResponseDTO getGoalById(String email, String id) {
        User user = getCurrentUser(email);
        Goal goal = goalRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with id: " + id));
        if (!goal.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You don't have access to this goal");
        }
        return toDto(goal);
    }

    public GoalResponseDTO updateById(GoalUpdateDTO dto, String email, String id) {
        User user = getCurrentUser(email);
        Goal goal = goalRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with id: " + id));
        if (!goal.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You don't have access to this goal");
        }
        if (dto.getGoalType() != null) goal.setGoalType(dto.getGoalType());
        if (dto.getTargetValue() != null) goal.setTargetValue(dto.getTargetValue());
        if (dto.getCurrentValue() != null) goal.setCurrentValue(dto.getCurrentValue());
        if (dto.getUnit() != null) goal.setUnit(dto.getUnit());
        if (dto.getStartDate() != null) goal.setStartDate(dto.getStartDate());
        if (dto.getTargetDate() != null) goal.setTargetDate(dto.getTargetDate());
        if (dto.getStatus() != null) goal.setStatus(dto.getStatus());

        goalRepository.save(goal);
        return toDto(goal);
    }

    public void deleteById(String email, String id) {
        User user = getCurrentUser(email);
        Goal goal = goalRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with id: " + id));
        if (!goal.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You don't have access to this goal");
        }
        goalRepository.delete(goal);
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