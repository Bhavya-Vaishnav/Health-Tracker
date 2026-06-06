package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.Reminder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReminderRepository extends MongoRepository<Reminder, ObjectId> {
}

