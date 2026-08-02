package com.bhavya.healthtracker.service;

import com.bhavya.healthtracker.dto.notificationpreferenceDTOs.NotificationPreferenceResponseDTO;
import com.bhavya.healthtracker.dto.notificationpreferenceDTOs.NotificationPreferenceUpdateDTO;
import com.bhavya.healthtracker.entity.NotificationPreference;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.repository.NotificationPreferenceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationPreferenceService {

    private final UserService userService;
    private final NotificationPreferenceRepository notificationPreferenceRepository;

    private NotificationPreference getOrCreatePreference(String email) {
        User user = getCurrentUser(email);
        NotificationPreference preference = notificationPreferenceRepository.findByUserId(user.getId());
        if (preference == null) {
            preference = NotificationPreference.builder()
                    .userId(user.getId())
                    .weeklyEmailEnabled(true)
                    .reminderEmailEnabled(true)
                    .timezone("Asia/Kolkata")  // ← fixed
                    .build();
            notificationPreferenceRepository.save(preference);
        }
        return preference;
    }

    public NotificationPreferenceResponseDTO getOrCreate(String email) {
        return toDto(getOrCreatePreference(email));
    }

    public NotificationPreferenceResponseDTO togglePreference(String email, NotificationPreferenceUpdateDTO dto) {
        NotificationPreference preference = getOrCreatePreference(email);

        if (dto.getWeeklyEmailEnabled() != null) {
            preference.setWeeklyEmailEnabled(dto.getWeeklyEmailEnabled());
        }
        if (dto.getReminderEmailEnabled() != null) {
            preference.setReminderEmailEnabled(dto.getReminderEmailEnabled());
        }
        if (dto.getTimezone() != null) {
            preference.setTimezone(dto.getTimezone());
        }
        notificationPreferenceRepository.save(preference);
        return toDto(preference);
    }

    private User getCurrentUser(String email) {
        return userService.findByEmail(email);
    }

    private NotificationPreferenceResponseDTO toDto(NotificationPreference preference) {
        return NotificationPreferenceResponseDTO.builder()
                .weeklyEmailEnabled(preference.isWeeklyEmailEnabled())
                .reminderEmailEnabled(preference.isReminderEmailEnabled())
                .timezone(preference.getTimezone())
                .build();
    }
}