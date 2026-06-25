package com.bhavya.healthtracker.dto.weeklyinsightDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeeklyInsightResponseDTO {
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private double avgCalories;
    private int totalExerciseMinutes;
    private double avgWaterMl;
    private double avgSleepHours;
}