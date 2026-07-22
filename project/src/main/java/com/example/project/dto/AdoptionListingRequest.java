package com.example.project.dto;

import lombok.Data;

@Data
public class AdoptionListingRequest {
    private Long animalReportId;
    private String animalName;
    private String description;
    private String city;
    private String imageUrl;              // ← added
}