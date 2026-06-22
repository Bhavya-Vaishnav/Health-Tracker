package com.bhavya.healthtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferenceUpdateDTO {

    private Boolean weeklyEmailEnabled;
    private Boolean reminderEmailEnabled;
    private String timezone;
}