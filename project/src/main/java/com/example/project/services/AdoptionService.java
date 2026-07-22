package com.example.project.services;

import com.example.project.dto.*;
import com.example.project.entity.*;

import com.example.project.Repository.*;

import com.example.project.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdoptionService {

    private final AdoptionListingRepository listingRepository;
    private final AdoptionApplicationRepository applicationRepository;
    private final AnimalReportRepository reportRepository;
    private final UserRepository userRepository;

    // ── LISTINGS ──────────────────────────────────────────

    // Create listing from rescued animal
    public AdoptionListingResponse createListing(
            AdoptionListingRequest request
    ) {

        AnimalReport report = reportRepository.findById(
                request.getAnimalReportId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Animal report not found"
                )
        );

        // Only RECOVERED animals can be listed
        if (report.getStatus() != AnimalReport.ReportStatus.RECOVERED) {

            throw new ResourceNotFoundException(
                    "Only recovered animals can be listed for adoption"
            );
        }

        AdoptionListing listing = new AdoptionListing();

        listing.setAnimalReport(report);
        listing.setAnimalName(request.getAnimalName());
        listing.setDescription(request.getDescription());
        listing.setCity(request.getCity());
        listing.setImageUrl(request.getImageUrl());

        AdoptionListing saved = listingRepository.save(listing);

        return mapListingToResponse(saved);
    }

    // Get all available listings
    public List<AdoptionListingResponse> getAvailableListings() {

        return listingRepository
                .findByStatus(
                        AdoptionListing.ListingStatus.AVAILABLE
                )
                .stream()
                .map(this::mapListingToResponse)
                .collect(Collectors.toList());
    }

    // Get listings by city
    public List<AdoptionListingResponse> getListingsByCity(
            String city
    ) {

        return listingRepository
                .findByCityAndStatus(
                        city,
                        AdoptionListing.ListingStatus.AVAILABLE
                )
                .stream()
                .map(this::mapListingToResponse)
                .collect(Collectors.toList());
    }

    // Get single listing
    public AdoptionListingResponse getListingById(Long id) {

        AdoptionListing listing = listingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Listing not found"
                        )
                );

        return mapListingToResponse(listing);
    }

    public AdoptionApplicationResponse rejectApplication(Long applicationId) {
    AdoptionApplication application = applicationRepository.findById(applicationId)
            .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

    application.setStatus(AdoptionApplication.ApplicationStatus.REJECTED);
    applicationRepository.save(application);
    return mapApplicationToResponse(application);
}
    // ── APPLICATIONS ──────────────────────────────────────

    // Apply for adoption
    public AdoptionApplicationResponse applyForAdoption(
            AdoptionApplicationRequest request,
            String userEmail
    ) {

        AdoptionListing listing = listingRepository.findById(
                request.getListingId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Listing not found"
                )
        );

        if (listing.getStatus()
                != AdoptionListing.ListingStatus.AVAILABLE) {

            throw new ResourceNotFoundException(
                    "This animal is no longer available"
            );
        }

        User applicant = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );

        AdoptionApplication application =
                new AdoptionApplication();

        application.setListing(listing);
        application.setApplicant(applicant);
        application.setMessage(request.getMessage());
        application.setPhone(request.getPhone());

        AdoptionApplication saved =
                applicationRepository.save(application);

        return mapApplicationToResponse(saved);
    }

    // Get my applications
    public List<AdoptionApplicationResponse> getMyApplications(
            String userEmail
    ) {

        return applicationRepository
                .findByApplicantEmail(userEmail)
                .stream()
                .map(this::mapApplicationToResponse)
                .collect(Collectors.toList());
    }

    // Get applications for listing
    public List<AdoptionApplicationResponse>
    getApplicationsForListing(Long listingId) {

        return applicationRepository.findByListingId(listingId)
                .stream()
                .map(this::mapApplicationToResponse)
                .collect(Collectors.toList());
    }

    // Approve application
    public AdoptionApplicationResponse approveApplication(
            Long applicationId
    ) {

        AdoptionApplication application =
                applicationRepository.findById(applicationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Application not found"
                        )
                );

        // Approve application
        application.setStatus(
                AdoptionApplication.ApplicationStatus.APPROVED
        );

        applicationRepository.save(application);

        // Mark listing adopted
        AdoptionListing listing = application.getListing();

        listing.setStatus(
                AdoptionListing.ListingStatus.ADOPTED
        );

        listingRepository.save(listing);

        // Update animal report
        AnimalReport report = listing.getAnimalReport();

        report.setStatus(
                AnimalReport.ReportStatus.ADOPTED
        );

        reportRepository.save(report);

        return mapApplicationToResponse(application);
    }

    // ── MAPPERS ───────────────────────────────────────────

    private AdoptionListingResponse mapListingToResponse(
            AdoptionListing listing
    ) {

        String animalType =
                listing.getAnimalReport() != null
                ? listing.getAnimalReport().getAnimalType()
                : "Unknown";

        return new AdoptionListingResponse(
                listing.getId(),
                listing.getAnimalName(),
                animalType,
                listing.getDescription(),
                listing.getCity(),
                listing.getStatus().name(),
                listing.getCreatedAt(),
                listing.getImageUrl()  
        );
    }

    private AdoptionApplicationResponse mapApplicationToResponse(
            AdoptionApplication app
    ) {

        return new AdoptionApplicationResponse(
                app.getId(),
                app.getListing().getAnimalName(),
                app.getApplicant().getName(),
                app.getApplicant().getEmail(),
                app.getMessage(),
                app.getPhone(),
                app.getStatus().name(),
                app.getAppliedAt()
        );
    }
}