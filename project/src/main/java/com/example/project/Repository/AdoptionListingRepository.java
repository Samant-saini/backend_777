package com.example.project.Repository;

import com.example.project.entity.AdoptionListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdoptionListingRepository extends JpaRepository<AdoptionListing, Long> {
    List<AdoptionListing> findByStatus(AdoptionListing.ListingStatus status);
    List<AdoptionListing> findByCity(String city);
    List<AdoptionListing> findByCityAndStatus(String city, AdoptionListing.ListingStatus status);
}