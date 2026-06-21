package com.bhavya.healthtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class WeeklyInsightResponseDTO {
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private double avgCalories;
    private int totalExerciseMinutes;
    private double avgWaterMl;
    private double avgSleepHours;
}