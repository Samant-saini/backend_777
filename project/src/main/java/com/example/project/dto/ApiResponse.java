package com.example.project.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;        // holds actual response data, null on error
    private int status;

    // Success response
    public static <T> ApiResponse<T> success(T data, String message, int status) {
        return new ApiResponse<>(true, message, data, status);
    }

    // Error response
    public static <T> ApiResponse<T> error(String message, int status) {
        return new ApiResponse<>(false, message, null, status);
    }
}