package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.Goal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GoalRepository extends MongoRepository<Goal, ObjectId> {
}

