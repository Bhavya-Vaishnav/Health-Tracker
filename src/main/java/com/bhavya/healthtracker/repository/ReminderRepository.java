package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.Reminder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public interface ReminderRepository extends MongoRepository<Reminder, ObjectId> {
    List<Reminder> findByUserId(ObjectId userId);

    List<Reminder> findByActiveTrueAndReminderTimeAndDaysOfWeekContaining(
            LocalTime reminderTime,
            DayOfWeek dayOfWeek
    );
}

