package com.bhavya.healthtracker.service;

import com.bhavya.healthtracker.dto.NotificationPreferenceResponseDTO;
import com.bhavya.healthtracker.dto.NotificationPreferenceUpdateDTO;
import com.bhavya.healthtracker.entity.NotificationPreference;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.repository.NotificationPreferenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationPreferenceService {

    @Autowired
    private UserService userService;
    @Autowired
    private NotificationPreferenceRepository notificationPreferenceRepository;

    public ResponseEntity<NotificationPreferenceResponseDTO> getOrCreate(String email) {
        User user = getCurrentUser(email);
        NotificationPreference notificationPreference = notificationPreferenceRepository.findByUserId(user.getId());
        if (notificationPreference == null) {
            NotificationPreference preference = NotificationPreference.builder()
                    .userId(user.getId())
                    .weeklyEmailEnabled(true)
                    .reminderEmailEnabled(true)
                    .build();
            notificationPreferenceRepository.save(preference);
            return new ResponseEntity<>(toDto(preference), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(toDto(notificationPreference), HttpStatus.OK);
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

    public ResponseEntity<NotificationPreferenceResponseDTO> togglePreference(String email, NotificationPreferenceUpdateDTO dto) {
        User user = getCurrentUser(email);
        NotificationPreference preference = notificationPreferenceRepository.findByUserId(user.getId());
        if (dto.getWeeklyEmailEnabled()!=null){
            preference.setWeeklyEmailEnabled(dto.getWeeklyEmailEnabled());
        }
        if (dto.getReminderEmailEnabled()!=null){
            preference.setReminderEmailEnabled(dto.getReminderEmailEnabled());
        }
        if (dto.getTimezone()!=null){
            preference.setTimezone(dto.getTimezone());
        }
        notificationPreferenceRepository.save(preference);
        return new ResponseEntity<>(toDto(preference),HttpStatus.OK);
    }
}
