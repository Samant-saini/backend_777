package com.example.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="animal_reports")
@NoArgsConstructor
@AllArgsConstructor
public class AnimalReport {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String  animalType;

    @Column(nullable = false)
    private String location;
    
    @Column(columnDefinition = "TEXT")
    private String symptoms;

    @Column(columnDefinition = "TEXT")
    private String firstAidAdvice;

    @Enumerated(EnumType.STRING)
    private ReportStatus status;
    @ManyToOne
    @JoinColumn(name = "reported_by")
    private User reportedBy;         // which user submitted this

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        status = ReportStatus.REPORTED;
    }

    public enum ReportStatus {
        REPORTED,
        HELP_DISPATCHED,
        IN_TREATMENT,
        RECOVERED,
        ADOPTED
    }


    
}
