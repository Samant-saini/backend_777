package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AnimalReportResponse {
    private Long reportId;
    private String animalType;
    private String location;
    private String symptoms;
    private String firstAidAdvice;
    private String status;

    private LocalDateTime createdAt;

    
}
