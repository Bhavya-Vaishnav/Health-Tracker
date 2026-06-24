package com.bhavya.healthtracker.dto.goalDTOs;

import com.bhavya.healthtracker.enums.GoalStatus;
import com.bhavya.healthtracker.enums.GoalType;
import com.bhavya.healthtracker.enums.GoalUnit;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalRequestDTO {

    @NotNull(message = "Goal type is required")
    private GoalType goalType;

    @PositiveOrZero(message = "Target value cannot be negative")
    private double targetValue;

    @PositiveOrZero(message = "Current value cannot be negative")
    private double currentValue;

    @NotNull(message = "Unit is required")
    private GoalUnit unit;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "Target date is required")
    @FutureOrPresent(message = "Target date cannot be in the past")
    private LocalDate targetDate;

    @NotNull(message = "Status is required")
    private GoalStatus status;
}