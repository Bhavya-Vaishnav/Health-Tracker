package com.bhavya.healthTracker.repository;

import com.bhavya.healthTracker.entity.EmailAudit;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailAuditRepository extends MongoRepository<EmailAudit, ObjectId> {
}

