package com.bhavya.healthtracker.dto.healthlogDTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthLogRequestDTO {

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    @Min(value = 0, message = "Exercise minutes cannot be negative")
    @Max(value = 1440, message = "Exercise minutes cannot exceed 1440 (24 hours)")
    private int exerciseMinutes;

    @Min(value = 0, message = "Calories cannot be negative")
    private int caloriesIntake;

    @Min(value = 0, message = "Water intake cannot be negative")
    private int waterMl;

    @DecimalMin(value = "0.0", message = "Sleep hours cannot be negative")
    @DecimalMax(value = "24.0", message = "Sleep hours cannot exceed 24")
    private double sleepHours;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;
}