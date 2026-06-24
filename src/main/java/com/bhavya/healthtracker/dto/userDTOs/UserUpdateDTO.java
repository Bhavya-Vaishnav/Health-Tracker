package com.bhavya.healthtracker.dto.userDTOs;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String email;
    private String password;
    private Boolean enabled;
}