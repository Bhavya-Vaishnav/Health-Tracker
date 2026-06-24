package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.EmailAudit;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EmailAuditRepository extends MongoRepository<EmailAudit, ObjectId> {
    List<EmailAudit> findByUserId(ObjectId userId);
}

