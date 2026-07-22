package com.example.project.services;

import com.example.project.dto.AnimalReportRequest;
import com.example.project.dto.AnimalReportResponse;

import com.example.project.entity.AnimalReport;
import com.example.project.entity.User;
import com.example.project.exception.ResourceNotFoundException;

import com.example.project.Repository.AnimalReportRepository;
import com.example.project.Repository.UserRepository;



import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalReportService {

    private final AnimalReportRepository reportRepository;
    private final UserRepository userRepository;
    private final GeminiService geminiService;

    // Create new report + get AI first aid
    public AnimalReportResponse createReport(
            AnimalReportRequest request,
            String userEmail
    ) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        String firstAidAdvice = geminiService.getFirstAidAdvice(
                request.getAnimalType(),
                request.getSymptoms()
        );

        AnimalReport report = new AnimalReport();

        report.setAnimalType(request.getAnimalType());
        report.setLocation(request.getLocation());
        report.setSymptoms(request.getSymptoms());
        report.setFirstAidAdvice(firstAidAdvice);
        report.setReportedBy(user);

        AnimalReport saved = reportRepository.save(report);

        return mapToResponse(saved);
    }

    // Get single report
    public AnimalReportResponse getReport(Long id) {

        AnimalReport report = reportRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Report not found"));

        return mapToResponse(report);
    }
    // Get all reports (for VET/ADMIN)
public List<AnimalReportResponse> getAllReports() {
    return reportRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
}

    // Get all reports by logged-in user
    public List<AnimalReportResponse> getMyReports(String userEmail) {

        return reportRepository.findByReportedByEmail(userEmail)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Update report status
    public AnimalReportResponse updateStatus(
            Long reportId,
            String newStatus
    ) {

        AnimalReport report = reportRepository.findById(reportId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Report not found"));

        report.setStatus(
                AnimalReport.ReportStatus.valueOf(
                        newStatus.toUpperCase()
                )
        );

        AnimalReport updated = reportRepository.save(report);

        return mapToResponse(updated);
    }

    // Convert Entity -> DTO
    private AnimalReportResponse mapToResponse(AnimalReport report) {

        return new AnimalReportResponse(
                report.getId(),
                report.getAnimalType(),
                report.getLocation(),
                report.getSymptoms(),
                report.getFirstAidAdvice(),
                report.getStatus().name(),
                report.getCreatedAt()
        );
    }
}