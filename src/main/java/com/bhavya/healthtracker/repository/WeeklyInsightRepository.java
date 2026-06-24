package com.bhavya.healthtracker.repository;

import com.bhavya.healthtracker.entity.WeeklyInsight;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeeklyInsightRepository extends MongoRepository<WeeklyInsight, ObjectId> {
    List<WeeklyInsight> findByUserId(ObjectId userId);

    Optional<WeeklyInsight> findByUserIdAndWeekStartDate(ObjectId userId, LocalDate weekStart);
}

