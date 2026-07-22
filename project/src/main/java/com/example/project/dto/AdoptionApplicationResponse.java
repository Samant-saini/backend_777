package com.example.project.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdoptionApplicationResponse {
    private Long id;
    private String animalName;
    private String applicantName;
    private String applicantEmail;
    private String message;
    private String phone;
    private String status;
    private LocalDateTime appliedAt;
}