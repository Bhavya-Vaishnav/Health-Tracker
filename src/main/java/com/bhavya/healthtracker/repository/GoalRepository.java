package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.Goal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GoalRepository extends MongoRepository<Goal, ObjectId> {

    List<Goal> findByUserId(ObjectId userId);
}

