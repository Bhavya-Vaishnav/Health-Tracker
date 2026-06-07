package com.bhavya.healthtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthLogResponseDTO {

    private String id;
    private LocalDate date;
    private int exerciseMinutes;
    private int caloriesIntake;
    private int waterMl;
    private double sleepHours;
    private String notes;
}
