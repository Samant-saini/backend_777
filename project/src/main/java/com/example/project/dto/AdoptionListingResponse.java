package com.example.project.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdoptionListingResponse {
    private Long id;
    private String animalName;
    private String animalType;
    private String description;
    private String city;
    private String status;
    private LocalDateTime createdAt;
    private String imageUrl;              // ← added
}