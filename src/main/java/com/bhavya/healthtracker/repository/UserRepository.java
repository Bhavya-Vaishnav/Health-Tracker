package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>{

    User findByName(String name);
    User findOneById(ObjectId id);
    User findByEmail(String email);
}
