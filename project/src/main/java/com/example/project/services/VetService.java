package com.example.project.services;

import com.example.project.dto.VetRegisterRequest;
import com.example.project.dto.VetResponse;

import com.example.project.entity.Vet;

import com.example.project.Repository.VetRepository;

import com.example.project.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VetService {

    private final VetRepository vetRepository;

    // Add a new vet
    public VetResponse addVet(VetRegisterRequest request) {

        Vet vet = new Vet();

        vet.setName(request.getName());
        vet.setClinicName(request.getClinicName());
        vet.setPhone(request.getPhone());
        vet.setEmail(request.getEmail());
        vet.setAddress(request.getAddress());
        vet.setCity(request.getCity());

        vet.setLatitude(request.getLatitude());
        vet.setLongitude(request.getLongitude());

        vet.setEmergencyAvailable(
                request.getEmergencyAvailable()
        );

        vet.setOpenNow(
                request.getOpenNow()
        );

        vet.setSpecializations(
                request.getSpecializations()
        );

        vet.setTimings(
                request.getTimings()
        );

        Vet saved = vetRepository.save(vet);

        return mapToResponse(saved);
    }

    // Get all vets
    public List<VetResponse> getAllVets() {

        return vetRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get vets by city
    public  List<VetResponse> findByCity(String city) {

        return vetRepository.findByCityIgnoreCase(city)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get emergency vets only
    public List<VetResponse> getEmergencyVets() {

        return vetRepository.findByEmergencyAvailableTrue()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get nearby vets
    public List<VetResponse> getNearbyVets(
            Double lat,
            Double lng,
            Double radiusKm
    ) {

        return vetRepository.findVetsNearby(
                        lat,
                        lng,
                        radiusKm
                )
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get single vet by ID
    public VetResponse getVetById(Long id) {

        Vet vet = vetRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vet not found"
                        )
                );

        return mapToResponse(vet);
    }

    // Convert Entity -> DTO
    private VetResponse mapToResponse(Vet vet) {

        return new VetResponse(
                vet.getId(),
                vet.getName(),
                vet.getClinicName(),
                vet.getPhone(),
                vet.getEmail(),
                vet.getAddress(),
                vet.getCity(),
                vet.getEmergencyAvailable(),
                vet.getOpenNow(),
                vet.getSpecializations(),
                vet.getTimings(),
                vet.getLatitude(),
                vet.getLongitude()
        );
    }
}