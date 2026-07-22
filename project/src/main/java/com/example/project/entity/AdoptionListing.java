package com.example.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "adoption_listings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "animal_report_id")
    private AnimalReport animalReport;

    @Column(nullable = false)
    private String animalName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String city;

    @Column(name = "image_url")
    private String imageUrl;              // ← added

    @Enumerated(EnumType.STRING)
    private ListingStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = ListingStatus.AVAILABLE;
    }

    public enum ListingStatus {
        AVAILABLE,
        UNDER_REVIEW,
        ADOPTED
    }
}