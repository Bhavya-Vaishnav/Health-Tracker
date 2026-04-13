package com.bhavya.healthTracker.repository;

import com.bhavya.healthTracker.entity.Goal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GoalRepository extends MongoRepository<Goal, ObjectId> {
}

