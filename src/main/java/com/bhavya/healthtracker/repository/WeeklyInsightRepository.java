package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.WeeklyInsight;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WeeklyInsightRepository extends MongoRepository<WeeklyInsight, ObjectId> {
}

