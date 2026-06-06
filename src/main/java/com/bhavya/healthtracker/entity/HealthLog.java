package com.bhavya.healthtracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "health_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthLog {

    @Id
    private ObjectId id;

    @NonNull
    @Indexed
    private ObjectId userId;

    @NonNull
    @Indexed
    private LocalDate date;

    private int exerciseMinutes;
    private int caloriesIntake;
    private int waterMl;
    private double sleepHours;

    private String notes;
}
