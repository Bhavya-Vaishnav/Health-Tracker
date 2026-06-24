package com.bhavya.healthtracker.dto.reminderDTOs;

import com.bhavya.healthtracker.enums.DayOfWeek;
import com.bhavya.healthtracker.enums.ReminderType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReminderRequestDTO {

    @NotNull(message = "Reminder type is required")
    private ReminderType type;

    @Size(max = 200, message = "Message cannot exceed 200 characters")
    private String message;

    @NotNull(message = "Reminder time is required")
    private LocalTime reminderTime;

    @NotEmpty(message = "At least one day must be selected")
    private List<DayOfWeek> daysOfWeek;
}