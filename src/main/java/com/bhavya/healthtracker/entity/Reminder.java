package com.bhavya.healthtracker.entity;

import com.bhavya.healthtracker.enums.DayOfWeek;
import com.bhavya.healthtracker.enums.ReminderType;
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

    @Indexed
    private ObjectId userId;

    @NonNull
    private ReminderType type;

    @NonNull
    private String message;

    @NonNull
    private LocalTime reminderTime;

    private List<DayOfWeek> daysOfWeek;

    @Builder.Default
    private boolean active=true;
}
