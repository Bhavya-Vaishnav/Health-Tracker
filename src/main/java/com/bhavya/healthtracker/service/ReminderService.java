package com.bhavya.healthtracker.service;

import com.bhavya.healthtracker.dto.reminderDTOs.ReminderRequestDTO;
import com.bhavya.healthtracker.dto.reminderDTOs.ReminderResponseDTO;
import com.bhavya.healthtracker.dto.reminderDTOs.ReminderUpdateDTO;
import com.bhavya.healthtracker.entity.Reminder;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.exception.ResourceNotFoundException;
import com.bhavya.healthtracker.exception.UnauthorizedAccessException;
import com.bhavya.healthtracker.repository.ReminderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserService userService;

    public ReminderResponseDTO createReminder(ReminderRequestDTO dto, String email) {
        User user = getCurrentUser(email);
        Reminder reminder = Reminder.builder()
                .userId(user.getId())
                .type(dto.getType())
                .message(dto.getMessage())
                .reminderTime(dto.getReminderTime())
                .daysOfWeek(dto.getDaysOfWeek())
                .build();
        reminderRepository.save(reminder);
        return toDto(reminder);
    }

    public List<ReminderResponseDTO> getAllReminders(String email) {
        User user = getCurrentUser(email);
        return reminderRepository.findByUserId(user.getId())
                .stream().map(this::toDto).toList();
    }

    public ReminderResponseDTO getReminderById(String email, String id) {
        User user = getCurrentUser(email);
        Reminder reminder = reminderRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found with id: " + id));
        if (!reminder.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You don't have access to this reminder");
        }
        return toDto(reminder);
    }

    public ReminderResponseDTO updateById(ReminderUpdateDTO dto, String email, String id) {
        User user = getCurrentUser(email);
        Reminder reminder = reminderRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found with id: " + id));
        if (!reminder.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You don't have access to this reminder");
        }
        if (dto.getType() != null) reminder.setType(dto.getType());
        if (dto.getReminderTime() != null) reminder.setReminderTime(dto.getReminderTime());
        if (dto.getMessage() != null) reminder.setMessage(dto.getMessage());
        if (dto.getDaysOfWeek() != null) reminder.setDaysOfWeek(dto.getDaysOfWeek());
        if (dto.getActive() != null) reminder.setActive(dto.getActive());

        reminderRepository.save(reminder);
        return toDto(reminder);
    }

    public void deleteById(String email, String id) {
        User user = getCurrentUser(email);
        Reminder reminder = reminderRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found with id: " + id));
        if (!reminder.getUserId().equals(user.getId())) {
            throw new UnauthorizedAccessException("You don't have access to this reminder");
        }
        reminderRepository.delete(reminder);
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