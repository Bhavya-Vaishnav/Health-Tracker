package com.bhavya.healthtracker.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private String name;
    private String email;
    private boolean enabled;
}