package com.bhavya.healthTracker.repository;

import com.bhavya.healthTracker.entity.WeeklyInsight;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeeklyInsightRepository extends MongoRepository<WeeklyInsight, ObjectId> {
}

