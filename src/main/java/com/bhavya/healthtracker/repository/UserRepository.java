package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.dto.HealthLogResponseDTO;
import com.bhavya.healthtracker.entity.HealthLog;
import com.bhavya.healthtracker.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, ObjectId>{

    User findOneById(ObjectId id);
    User findByEmail(String email);
}
