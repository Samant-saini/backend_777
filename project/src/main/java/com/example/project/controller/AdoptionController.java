package com.example.project.controller;

import com.example.project.dto.*;
import com.example.project.services.AdoptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/adoptions")
@RequiredArgsConstructor
public class AdoptionController {

    private final AdoptionService adoptionService;

    // Create listing — POST /api/adoptions/listings
   @PostMapping("/listings")
public ResponseEntity<ApiResponse<AdoptionListingResponse>> createListing(
        @RequestBody AdoptionListingRequest request) {
    AdoptionListingResponse response = adoptionService.createListing(request);
    return ResponseEntity.ok(ApiResponse.success(response, "Listing created", 200));
}

    // Get all available — GET /api/adoptions/listings
    
  

    @GetMapping("/listings")
public ResponseEntity<ApiResponse<List<AdoptionListingResponse>>> getAvailable() {
    return ResponseEntity.ok(ApiResponse.success(
        adoptionService.getAvailableListings(), "Success", 200));
}

@GetMapping("/listings/city")
public ResponseEntity<ApiResponse<List<AdoptionListingResponse>>> getByCity(
        @RequestParam String name) {
    return ResponseEntity.ok(ApiResponse.success(
        adoptionService.getListingsByCity(name), "Success", 200));
}

@PostMapping("/apply")
public ResponseEntity<ApiResponse<AdoptionApplicationResponse>> apply(
        @RequestBody AdoptionApplicationRequest request,
        Authentication authentication) {
    return ResponseEntity.ok(ApiResponse.success(
        adoptionService.applyForAdoption(request, authentication.getName()), 
        "Application submitted", 200));
}

@GetMapping("/my-applications")
public ResponseEntity<ApiResponse<List<AdoptionApplicationResponse>>> myApplications(
        Authentication authentication) {
    return ResponseEntity.ok(ApiResponse.success(
        adoptionService.getMyApplications(authentication.getName()), "Success", 200));
}
      // Get single listing — GET /api/adoptions/listings/1
    @GetMapping("/listings/{id}")
    public ResponseEntity<AdoptionListingResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(adoptionService.getListingById(id));
    }

    // Applications for a listing — GET /api/adoptions/listings/1/applications
    @GetMapping("/listings/{id}/applications")
    public ResponseEntity<List<AdoptionApplicationResponse>> listingApplications(
            @PathVariable Long id) {
        return ResponseEntity.ok(adoptionService.getApplicationsForListing(id));
    }

    // Approve application — PUT /api/adoptions/applications/1/approve
    @PutMapping("/applications/{id}/approve")
    public ResponseEntity<AdoptionApplicationResponse> approve(@PathVariable Long id) {
        return ResponseEntity.ok(adoptionService.approveApplication(id));
    }
    // Reject application — PUT /api/adoptions/applications/1/reject
@PutMapping("/applications/{id}/reject")
public ResponseEntity<ApiResponse<AdoptionApplicationResponse>> reject(
        @PathVariable Long id) {
    return ResponseEntity.ok(ApiResponse.success(
        adoptionService.rejectApplication(id), "Application rejected", 200));
}
}