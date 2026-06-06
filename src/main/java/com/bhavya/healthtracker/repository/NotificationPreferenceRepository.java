package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.NotificationPreference;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationPreferenceRepository extends MongoRepository<NotificationPreference, ObjectId> {
}

