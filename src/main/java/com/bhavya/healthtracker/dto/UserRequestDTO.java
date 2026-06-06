package com.bhavya.healthtracker.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    @NonNull
    private String name;
    private String email;
    @NonNull
    private String password;
}
