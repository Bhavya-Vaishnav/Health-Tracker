package com.bhavya.healthtracker.dto.userDTOs;

import com.bhavya.healthtracker.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResponseDTO {
    private String name;
    private String email;
    private boolean enable;
    private List<UserRoles> roles;
}