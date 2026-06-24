package com.bhavya.healthtracker.dto.goalDTOs;

import com.bhavya.healthtracker.enums.GoalStatus;
import com.bhavya.healthtracker.enums.GoalType;
import com.bhavya.healthtracker.enums.GoalUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalResponseDTO {

    private String id;

    private GoalType goalType;

    private double targetValue;

    private double currentValue;

    private GoalUnit unit;

    private LocalDate startDate;

    private LocalDate targetDate;

    private GoalStatus status;
}