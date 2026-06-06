package com.bhavya.healthtracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "email_audit")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailAudit {

    @Id
    private ObjectId id;

    @NonNull
    @Indexed
    private ObjectId userId;

    @NonNull
    private String email;

    @NonNull
    private String subject;

    @NonNull
    private String status; // SENT, FAILED

    private String errorMessage;

    @NonNull
    private LocalDateTime sentAt;
}
