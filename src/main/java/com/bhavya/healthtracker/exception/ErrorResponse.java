package com.bhavya.healthtracker.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {

    private String message;
    private Map<String, String> errors;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
