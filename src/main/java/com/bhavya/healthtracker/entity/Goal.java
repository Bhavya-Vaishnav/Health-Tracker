package com.bhavya.healthtracker.entity;

import com.bhavya.healthtracker.enums.GoalStatus;
import com.bhavya.healthtracker.enums.GoalType;
import com.bhavya.healthtracker.enums.GoalUnit;
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

    @Indexed
    private ObjectId userId;

    @NonNull
    private GoalType goalType; // WEIGHT_LOSS, MUSCLE_GAIN, WATER, SLEEP

    private double targetValue;
    private double currentValue;

    @NonNull
    private GoalUnit unit; // kg, ml, hours, min

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate targetDate;

    private GoalStatus status;
}
