package com.bhavya.healthtracker.service;

import com.bhavya.healthtracker.dto.ReminderRequestDTO;
import com.bhavya.healthtracker.dto.ReminderResponseDTO;
import com.bhavya.healthtracker.dto.ReminderUpdateDTO;
import com.bhavya.healthtracker.entity.Reminder;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.repository.ReminderRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReminderService {

    @Autowired
    private ReminderRepository reminderRepository;
    @Autowired
    private UserService userService;

    public ResponseEntity<?> createReminder(ReminderRequestDTO dto, String email) {
        User user = getCurrentUser(email);
        Reminder reminder = Reminder.builder().userId(user.getId()).type(dto.getType()).message(dto.getMessage()).reminderTime(dto.getReminderTime()).daysOfWeek(dto.getDaysOfWeek()).build();
        reminderRepository.save(reminder);
        return new ResponseEntity<>(toDto(reminder), HttpStatus.CREATED);
    }

    public ResponseEntity<List<ReminderResponseDTO>> getAllLogs(String email) {
        try {
            User user = getCurrentUser(email);
            ObjectId userId = user.getId();
            List<Reminder> logs = reminderRepository.findByUserId(userId);
            List<ReminderResponseDTO> response =
                    logs.stream()
                            .map(this::toDto)
                            .toList();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error occurred during getting Reminders:", e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ReminderResponseDTO> getLogById(String email, String id) {
        User user = getCurrentUser(email);
        Reminder reminder = reminderRepository
                .findById(new ObjectId(id))
                .orElse(null);
        if (reminder != null) {
            if (!reminder.getUserId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                return new ResponseEntity<>(toDto(reminder), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ReminderResponseDTO> updateById(ReminderUpdateDTO dto, String email, String id) {
        User user = getCurrentUser(email);
        Reminder reminder = reminderRepository
                .findById(new ObjectId(id))
                .orElse(null);
        if (reminder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!reminder.getUserId().equals(user.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (dto.getType() != null)
            reminder.setType(dto.getType());
        if (dto.getReminderTime() != null)
            reminder.setReminderTime(dto.getReminderTime());
        if (dto.getMessage() != null)
            reminder.setMessage(dto.getMessage());
        if (dto.getDaysOfWeek() != null)
            reminder.setDaysOfWeek(dto.getDaysOfWeek());
        if (dto.getActive() != null)
            reminder.setActive(dto.getActive());
        reminderRepository.save(reminder);
        return ResponseEntity.ok(toDto(reminder));
    }

    public ResponseEntity<ReminderResponseDTO> deleteById(String email, String id) {
        User user = getCurrentUser(email);
        Reminder reminder = reminderRepository
                .findById(new ObjectId(id))
                .orElse(null);
        if (reminder != null) {
            if (!reminder.getUserId().equals(user.getId())) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else {
                reminderRepository.delete(reminder);
                return ResponseEntity.noContent().build();
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private User getCurrentUser(String email) {
        return userService.findByEmail(email);
    }

    private ReminderResponseDTO toDto(Reminder reminder) {
        return ReminderResponseDTO.builder()
                .id(reminder.getId().toHexString())
                .type(reminder.getType())
                .message(reminder.getMessage())
                .reminderTime(reminder.getReminderTime())
                .daysOfWeek(reminder.getDaysOfWeek())
                .active(reminder.isActive())
                .build();
    }

}
