package com.bhavya.healthtracker.service;

import com.bhavya.healthtracker.dto.goalDTOs.GoalResponseDTO;
import com.bhavya.healthtracker.dto.notificationpreferenceDTOs.NotificationPreferenceResponseDTO;
import com.bhavya.healthtracker.dto.weeklyinsightDTO.WeeklyInsightResponseDTO;
import com.bhavya.healthtracker.entity.EmailAudit;
import com.bhavya.healthtracker.entity.Reminder;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.enums.EmailStatus;
import com.bhavya.healthtracker.enums.EmailType;
import com.bhavya.healthtracker.repository.EmailAuditRepository;
import com.bhavya.healthtracker.repository.ReminderRepository;
import com.bhavya.healthtracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.bhavya.healthtracker.enums.GoalStatus.COMPLETED;
import static com.bhavya.healthtracker.enums.GoalStatus.IN_PROGRESS;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final  JavaMailSender javaMailSender;
    private final  UserRepository userRepository;
    private final  NotificationPreferenceService notificationPreferenceService;
    private final  WeeklyInsightService weeklyInsightService;
    private final  ReminderRepository reminderRepository;
    private final  GoalService goalService;
    private final  EmailAuditRepository emailAuditRepository;

    public boolean sendEmail(String to, String subject, String body, ObjectId userId, EmailType emailType) {
        EmailAudit audit = EmailAudit.builder()
                .userId(userId)
                .toEmail(to)
                .subject(subject)
                .emailType(emailType)
                .sentAt(LocalDateTime.now())
                .build();
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);
            javaMailSender.send(mail);

            audit.setStatus(EmailStatus.SENT);
            emailAuditRepository.save(audit);

            return true;
        } catch (Exception e) {
            log.error("Error sending mail:", e);
            audit.setStatus(EmailStatus.FAILED);
            audit.setErrorMessage(e.getMessage());
            emailAuditRepository.save(audit);
            return false;
        }
    }


    @Scheduled(fixedRate = 60000)
    public void processReminders() {
        LocalTime time = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        List<Reminder> reminders = reminderRepository.findByActiveTrueAndReminderTimeAndDaysOfWeekContaining(time, dayOfWeek);
        for (Reminder r : reminders) {
            User user = userRepository.findOneById(r.getUserId());
            if (user == null) continue;
            NotificationPreferenceResponseDTO preference = notificationPreferenceService.getOrCreate(user.getEmail());
            if (preference.isReminderEmailEnabled()) {
                sendReminderEmail(user.getName(), r, user.getEmail(), user.getId());
            }
        }
    }

    public void sendReminderEmail(
            String name,
            Reminder reminder,
            String to,
            ObjectId userId) {

        String body = """
                Hello %s,
                
                This is your scheduled health reminder.
                
                Reminder Type: %s
                
                Message:
                %s
                
                Reminder Time: %s
                
                Stay consistent and keep tracking your health.
                
                - HealthTracker
                """
                .formatted(
                        name,
                        reminder.getType(),
                        reminder.getMessage(),
                        reminder.getReminderTime()
                );

        sendEmail(
                to,
                "HealthTracker - " + reminder.getType() + " Reminder",
                body,
                userId,
                EmailType.REMINDER
        );
    }

    @Scheduled(cron = "0 0 23 * * SUN")
    public void processWeeklyInsights() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            NotificationPreferenceResponseDTO preference = notificationPreferenceService.getOrCreate(user.getEmail());
            if (preference.isWeeklyEmailEnabled()) {
                sendWeeklyInsightMail(user.getName(), user.getEmail(), user.getId());
            }
        }
    }

    public void sendWeeklyInsightMail(String name, String email, ObjectId userid) {
        WeeklyInsightResponseDTO insight = weeklyInsightService.generateCurrentWeekInsight(email);
        List<GoalResponseDTO> allGoals = goalService.getAllGoals(email);
        int active = 0;
        int completed = 0;
        if (!allGoals.isEmpty()) {
            for (GoalResponseDTO goal : allGoals) {
                if (goal.getStatus() == IN_PROGRESS) {
                    active++;
                }
                if (goal.getStatus() == COMPLETED) {
                    completed++;
                }
            }
        }

        String body = """
                Hello %s,
                
                Here's your health summary for the week
                (%s - %s)
                
                ━━━━━━━━━━━━━━━━━━━━━━
                
                📈 Weekly Statistics
                
                • Average Daily Calories: %s cal
                • Total Exercise Time: %s minutes
                • Average Water Intake(Ml): %s ml
                • Average Sleep Duration: %s hours
                
                ━━━━━━━━━━━━━━━━━━━━━━
                
                🎯 Goal Progress
                
                • Active Goals: %s
                • Completed Goals: %s
                
                ━━━━━━━━━━━━━━━━━━━━━━
                
                Keep up the good work!
                
                Regards,
                HealthTracker Team
                """.formatted(name, insight.getWeekStartDate(), insight.getWeekEndDate(), insight.getAvgCalories(), insight.getTotalExerciseMinutes(), insight.getAvgWaterMl(), insight.getAvgSleepHours(), active, completed);

        sendEmail(email, "HealthTracker - Weekly Insight", body, userid, EmailType.WEEKLY_INSIGHT);
    }
}
