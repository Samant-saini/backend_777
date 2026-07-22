package com.example.project.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AnimalReportRequest {

    @NotBlank(message = "Animal type is required")
    private String animalType;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Symptoms are required")
    @Size(min = 10, message = "Please describe symptoms in at least 10 characters")
    private String symptoms;
}