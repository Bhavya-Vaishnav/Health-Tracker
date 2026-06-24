package com.bhavya.healthtracker.dto.reminderDTOs;

import com.bhavya.healthtracker.enums.DayOfWeek;
import com.bhavya.healthtracker.enums.ReminderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReminderUpdateDTO {

    private ReminderType type;

    private String message;

    private LocalTime reminderTime;

    private List<DayOfWeek> daysOfWeek;

    private Boolean active;
}