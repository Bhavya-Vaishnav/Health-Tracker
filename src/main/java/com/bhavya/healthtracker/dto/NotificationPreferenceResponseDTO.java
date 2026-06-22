package com.bhavya.healthtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferenceResponseDTO {

    private boolean weeklyEmailEnabled;
    private boolean reminderEmailEnabled;
    private String timezone;
}