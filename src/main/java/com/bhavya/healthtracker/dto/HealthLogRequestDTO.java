package com.bhavya.healthtracker.dto;

import com.bhavya.healthtracker.entity.HealthLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthLogRequestDTO {

    @NonNull
    private LocalDate date;

    private int exerciseMinutes;
    private int caloriesIntake;
    private int waterMl;
    private double sleepHours;

    private String notes;
}
