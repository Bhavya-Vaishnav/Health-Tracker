package com.bhavya.healthTracker.repository;

import com.bhavya.healthTracker.entity.NotificationPreference;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationPreferenceRepository extends MongoRepository<NotificationPreference, ObjectId> {
}

