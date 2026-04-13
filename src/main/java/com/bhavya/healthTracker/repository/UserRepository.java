package com.bhavya.healthTracker.repository;

import com.bhavya.healthTracker.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>{

    User findByName(String name);
    User findOneById(ObjectId id);
}
