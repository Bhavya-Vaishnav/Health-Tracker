package com.bhavya.healthtracker.dto;

import com.bhavya.healthtracker.entity.HealthLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String name;
    private String email;

    private List<HealthLogResponseDTO> healthLogs=new ArrayList<>();
}