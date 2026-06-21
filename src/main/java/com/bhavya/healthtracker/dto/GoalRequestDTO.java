package com.bhavya.healthtracker.dto;

import com.bhavya.healthtracker.enums.GoalStatus;
import com.bhavya.healthtracker.enums.GoalType;
import com.bhavya.healthtracker.enums.GoalUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoalRequestDTO {

    private GoalType goalType;

    private double targetValue;

    private double currentValue;

    private GoalUnit unit;

    private LocalDate startDate;

    private LocalDate targetDate;
    private GoalStatus status;
}
