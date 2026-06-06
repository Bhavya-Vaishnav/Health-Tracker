package com.bhavya.healthtracker.dto;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String email;
    private String password;
    private Boolean enabled;
}