package com.bhavya.healthTracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notification_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationPreference {

    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private ObjectId userId;

    private boolean weeklyEmailEnabled;
    private boolean reminderEmailEnabled;

    @NonNull
    private String timezone; // Asia/Kolkata
}
