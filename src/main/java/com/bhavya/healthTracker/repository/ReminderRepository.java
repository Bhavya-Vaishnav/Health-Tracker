package com.bhavya.healthTracker.repository;

import com.bhavya.healthTracker.entity.Reminder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReminderRepository extends MongoRepository<Reminder, ObjectId> {
}

