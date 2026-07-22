package com.example.project.dto;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class VetResponse {
    private Long id;
    private String name;
    private String clinicName;
    private String phone;
    private String email;
    private String address;
    private String city;
    private Boolean emergencyAvailable;
    private Boolean openNow;
    private String specializations;
    private String timings;
    private Double latitude;
    private Double longitude;
}