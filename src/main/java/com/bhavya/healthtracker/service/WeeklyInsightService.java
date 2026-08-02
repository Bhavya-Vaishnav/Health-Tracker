package com.bhavya.healthtracker.service;

import com.bhavya.healthtracker.dto.weeklyinsightDTO.WeeklyInsightResponseDTO;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.entity.WeeklyInsight;
import com.bhavya.healthtracker.repository.WeeklyInsightRepository;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WeeklyInsightService {

    private final MongoTemplate mongoTemplate;
    private final UserService userService;
    private final WeeklyInsightRepository weeklyInsightRepository;

    @Cacheable(value = "weeklyInsight", key = "#email")
    public WeeklyInsightResponseDTO generateCurrentWeekInsight(String email) {
        User user = userService.findByEmail(email);
        ObjectId userId = user.getId();

        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = today.with(DayOfWeek.SUNDAY);

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userId").is(userId)
                        .and("date").gte(weekStart).lte(weekEnd)),
                Aggregation.group()
                        .avg("caloriesIntake").as("avgCalories")
                        .sum("exerciseMinutes").as("totalExercise")
                        .avg("sleepHours").as("avgSleep")
                        .avg("waterMl").as("avgWaterMl")
        );

        AggregationResults<Document> result =
                mongoTemplate.aggregate(agg, "health_logs", Document.class);

        Document doc = result.getUniqueMappedResult();

        if (doc == null) {
            // no logs this week
            return new WeeklyInsightResponseDTO(weekStart, weekEnd, 0, 0, 0,0);
        }

        double avgCal = doc.getDouble("avgCalories");
        int totalEx = doc.get("totalExercise", Number.class).intValue();
        double avgSleep = doc.getDouble("avgSleep");
        double avgWater=doc.getDouble("avgWaterMl");

        WeeklyInsight existing = weeklyInsightRepository
                .findByUserIdAndWeekStartDate(userId, weekStart)
                .orElse(null);

        WeeklyInsight weeklyInsight = (existing != null) ? existing : new WeeklyInsight();
        weeklyInsight.setUserId(userId);
        weeklyInsight.setWeekStartDate(weekStart);
        weeklyInsight.setWeekEndDate(weekEnd);
        weeklyInsight.setAvgCalories(avgCal);
        weeklyInsight.setTotalExerciseMinutes(totalEx);
        weeklyInsight.setAvgWaterMl(avgWater);
        weeklyInsight.setAvgSleepHours(avgSleep);

        weeklyInsightRepository.save(weeklyInsight);
        return new WeeklyInsightResponseDTO(weekStart, weekEnd, avgCal, totalEx,avgWater, avgSleep);
    }

}
