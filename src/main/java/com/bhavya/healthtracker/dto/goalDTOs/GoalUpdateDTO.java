package com.bhavya.healthtracker.dto.goalDTOs;

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
public class GoalUpdateDTO {

    private GoalType goalType;

    private Double targetValue;

    private Double currentValue;

    private GoalUnit unit;

    private LocalDate startDate;

    private LocalDate targetDate;

    private GoalStatus status;
}
