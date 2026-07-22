package com.example.project.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name="vet")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vet{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String clinicName;

    @Column(nullable = false)
    private String phone;

    @Column 
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Double latitude;          // for distance calculation

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private Boolean emergencyAvailable;   // available 24/7?

    @Column(nullable = false)
    private Boolean openNow;
     @Column
    private String specializations;   // "dog,cat,bird,exotic" — comma separated

    @Column
    private String timings;
    

}