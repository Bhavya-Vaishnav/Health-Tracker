package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.NotificationPreference;
import com.bhavya.healthtracker.entity.Reminder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationPreferenceRepository extends MongoRepository<NotificationPreference, ObjectId> {
    NotificationPreference findByUserId(ObjectId userId);
}

