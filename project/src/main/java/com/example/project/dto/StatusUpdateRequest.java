package com.example.project.dto;

import lombok.Data;

@Data
public class StatusUpdateRequest {
    private String status;  // HELP_DISPATCHED, IN_TREATMENT, RECOVERED, ADOPTED
}