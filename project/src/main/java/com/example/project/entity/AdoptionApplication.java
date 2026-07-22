package com.example.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "adoption_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "listing_id")
    private AdoptionListing listing;     // which animal

    @ManyToOne
    @JoinColumn(name = "applicant_id")
    private User applicant;              // who wants to adopt

    @Column(columnDefinition = "TEXT")
    private String message;              // why do you want to adopt?

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    @Column(name = "applied_at")
    private LocalDateTime appliedAt;

    @PrePersist
    protected void onCreate() {
        appliedAt = LocalDateTime.now();
        status = ApplicationStatus.PENDING;
    }

    public enum ApplicationStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}