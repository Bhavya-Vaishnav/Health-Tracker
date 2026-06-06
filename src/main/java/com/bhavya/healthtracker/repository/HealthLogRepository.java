package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.HealthLog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HealthLogRepository extends MongoRepository<HealthLog, ObjectId>{
}
