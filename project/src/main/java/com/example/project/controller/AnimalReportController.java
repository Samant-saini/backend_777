package com.example.project.controller;

import com.example.project.dto.AnimalReportRequest;
import com.example.project.dto.AnimalReportResponse;
import com.example.project.dto.ApiResponse;
import com.example.project.dto.StatusUpdateRequest;
import com.example.project.services.AnimalReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class AnimalReportController {

    private final AnimalReportService reportService;

    @PostMapping
public ResponseEntity<ApiResponse<AnimalReportResponse>> createReport(
        @Valid @RequestBody AnimalReportRequest request,
        Authentication authentication) {
    AnimalReportResponse response = reportService.createReport(request, authentication.getName());
    return ResponseEntity.ok(ApiResponse.success(response, "Report created successfully", 200));
}
@GetMapping
public ResponseEntity<ApiResponse<List<AnimalReportResponse>>> getAllReports(
        Authentication authentication) {
    return ResponseEntity.ok(ApiResponse.success(
        reportService.getAllReports(), "Success", 200));
}

@GetMapping("/{id}")
public ResponseEntity<ApiResponse<AnimalReportResponse>> getReport(@PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success(reportService.getReport(id), "Success", 200));
}

@GetMapping("/my")
public ResponseEntity<ApiResponse<List<AnimalReportResponse>>> getMyReports(
        Authentication authentication) {
    return ResponseEntity.ok(ApiResponse.success(
        reportService.getMyReports(authentication.getName()), "Success", 200));
}

@PutMapping("/{id}/status")
public ResponseEntity<ApiResponse<AnimalReportResponse>> updateStatus(
        @PathVariable Long id,
        @RequestBody StatusUpdateRequest request) {
    return ResponseEntity.ok(ApiResponse.success(
        reportService.updateStatus(id, request.getStatus()), "Status updated", 200));
}
}