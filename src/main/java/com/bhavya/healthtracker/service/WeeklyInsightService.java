package com.bhavya.healthtracker.service;

import com.bhavya.healthtracker.dto.WeeklyInsightResponseDTO;
import com.bhavya.healthtracker.entity.User;
import com.bhavya.healthtracker.entity.WeeklyInsight;
import com.bhavya.healthtracker.repository.UserRepository;
import com.bhavya.healthtracker.repository.WeeklyInsightRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class WeeklyInsightService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeeklyInsightRepository weeklyInsightRepository;

    public WeeklyInsightResponseDTO generateCurrentWeekInsight(String email) {
        User user = userRepository.findByEmail(email);
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
        int totalEx = doc.getInteger("totalExercise");
        double avgSleep = doc.getDouble("avgSleep");
        double avgWater=doc.getDouble("avgWaterMl");
        WeeklyInsight weeklyInsight = WeeklyInsight.builder().userId(userId).weekStartDate(weekStart).weekEndDate(weekEnd).avgCalories(avgCal).avgWaterMl(avgWater).avgSleepHours(avgSleep).build();
        weeklyInsightRepository.save(weeklyInsight);
        return new WeeklyInsightResponseDTO(weekStart, weekEnd, avgCal, totalEx,avgWater, avgSleep);
    }

    @Scheduled(cron = "0 0 23 * * SUN")
    public void generateWeeklyInsightsForAllUsers() {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            WeeklyInsightResponseDTO dto = generateCurrentWeekInsight(user.getEmail());
            // convert dto → entity → save to weekly_insights collection
        }
    }

}
