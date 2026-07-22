package com.example.project.dto;

import lombok.Data;


@Data
public class VetRegisterRequest {
    private String name;
    private String clinicName;
    private String phone;
    private String email;
    private String address;
    private String city;
    private Double latitude;
    private Double longitude;
    private Boolean emergencyAvailable;
    private Boolean openNow;
    private String specializations;  
    private String timings;
}