package com.bhavya.healthtracker.entity;

import com.bhavya.healthtracker.enums.EmailStatus;
import com.bhavya.healthtracker.enums.EmailType;
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

    @Indexed
    private ObjectId userId;

    @NonNull
    private String toEmail;

    @NonNull
    private EmailType emailType;

    @NonNull
    private String subject;

    private EmailStatus status;

    private String errorMessage;

    @Indexed
    private LocalDateTime sentAt;
}
