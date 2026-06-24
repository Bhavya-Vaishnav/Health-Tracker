package com.bhavya.healthtracker;

import com.bhavya.healthtracker.enums.EmailType;
import com.bhavya.healthtracker.service.EmailService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void sendEmailTest() {
        emailService.sendEmail(
                "vaishnavbhavya2007@gmail.com",
                "",
                "Email service is working!",
                new ObjectId(), EmailType.REMINDER);
    }
}
