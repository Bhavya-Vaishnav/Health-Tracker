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

import java.time.LocalTime;
import java.util.List;

@Document(collection = "reminders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reminder {

    @Id
    private ObjectId id;

    @NonNull
    @Indexed
    private ObjectId userId;

    @NonNull
    private String type; // WATER, EXERCISE, SLEEP, LOG_ENTRY

    @NonNull
    private String message;

    @NonNull
    private LocalTime reminderTime;

    private List<String> daysOfWeek;

    private boolean active;
}
