package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.EmailAudit;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailAuditRepository extends MongoRepository<EmailAudit, ObjectId> {
}

