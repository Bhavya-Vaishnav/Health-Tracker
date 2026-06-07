package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.HealthLog;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HealthLogRepository
        extends MongoRepository<HealthLog, ObjectId> {

    List<HealthLog> findByUserId(ObjectId userId);
    HealthLog findById(String id);
}
