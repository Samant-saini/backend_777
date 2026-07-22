package com.example.project.Repository;

import com.example.project.entity.AdoptionApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdoptionApplicationRepository extends JpaRepository<AdoptionApplication, Long> {
    List<AdoptionApplication> findByApplicantEmail(String email);
    List<AdoptionApplication> findByListingId(Long listingId);
}