package com.bhavya.healthtracker.dto;

import com.bhavya.healthtracker.enums.DayOfWeek;
import com.bhavya.healthtracker.enums.ReminderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderResponseDTO {

    private String id;

    private ReminderType type;

    private String message;

    private LocalTime reminderTime;

    private List<DayOfWeek> daysOfWeek;

    private boolean active;
}
