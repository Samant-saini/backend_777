
package com.example.project.controller;

import com.example.project.dto.VetRegisterRequest;
import com.example.project.dto.VetResponse;
import com.example.project.services.VetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vets")
@RequiredArgsConstructor
public class VetController {

    private final VetService vetService;

    // Add vet — POST /api/vets
    @PostMapping
    public ResponseEntity<VetResponse> addVet(@RequestBody VetRegisterRequest request) {
        return ResponseEntity.ok(vetService.addVet(request));
    }

    // Get all vets — GET /api/vets
    @GetMapping
    public ResponseEntity<List<VetResponse>> getAllVets() {
        return ResponseEntity.ok(vetService.getAllVets());
    }

    // Get by city — GET /api/vets/city?name=Dehradun
    @GetMapping("/city")
    public ResponseEntity<List<VetResponse>> getByCity(@RequestParam String name) {
        return ResponseEntity.ok(vetService.findByCity(name));
    }

    // Get emergency vets — GET /api/vets/emergency
    @GetMapping("/emergency")
    public ResponseEntity<List<VetResponse>> getEmergencyVets() {
        return ResponseEntity.ok(vetService.getEmergencyVets());
    }

    // Get nearby vets — GET /api/vets/nearby?lat=30.31&lng=78.03&radius=5
    @GetMapping("/nearby")
    public ResponseEntity<List<VetResponse>> getNearbyVets(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "5.0") Double radius) {
        return ResponseEntity.ok(vetService.getNearbyVets(lat, lng, radius));
    }

    // Get single vet — GET /api/vets/1
    @GetMapping("/{id}")
    public ResponseEntity<VetResponse> getVetById(@PathVariable Long id) {
        return ResponseEntity.ok(vetService.getVetById(id));
    }
}