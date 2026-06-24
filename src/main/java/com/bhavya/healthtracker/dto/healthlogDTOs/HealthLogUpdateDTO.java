package com.bhavya.healthtracker.dto.healthlogDTOs;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthLogUpdateDTO {

    private LocalDate date;
    private Integer exerciseMinutes;
    private Integer caloriesIntake;
    private Integer waterMl;
    private Double sleepHours;
    private String notes;
}
