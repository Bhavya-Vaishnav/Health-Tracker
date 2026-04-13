package com.bhavya.healthTracker.entity;

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

@Document(collection = "goals")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {

    @Id
    private ObjectId id;

    @NonNull
    @Indexed
    private ObjectId userId;

    @NonNull
    private String goalType; // WEIGHT_LOSS, MUSCLE_GAIN, WATER, SLEEP

    private double targetValue;
    private double currentValue;

    @NonNull
    private String unit; // kg, ml, hours, min

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate targetDate;

    private String status;
}
