package com.example.project.dto;

import lombok.Data;

@Data
public class AdoptionApplicationRequest {
    private Long listingId;
    private String message;
    private String phone;
}