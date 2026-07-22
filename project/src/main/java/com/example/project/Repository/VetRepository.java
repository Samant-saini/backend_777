package com.example.project.Repository;

import com.example.project.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VetRepository extends JpaRepository<Vet, Long> {

    // Find vets by city
    List<Vet> findByCity(String city);
    List<Vet> findByCityIgnoreCase(String city);

    // Find emergency vets only
    List<Vet> findByEmergencyAvailableTrue();

    // Find vets by city + emergency
    List<Vet> findByCityAndEmergencyAvailableTrue(String city);

    // Find nearby vets using Haversine formula (calculates real distance)
    @Query(value = """
        SELECT *, (
            6371 * acos(
                cos(radians(:lat)) * cos(radians(latitude)) *
                cos(radians(longitude) - radians(:lng)) +
                sin(radians(:lat)) * sin(radians(latitude))
            )
        ) AS distance
        FROM vets
        WHERE (
            6371 * acos(
                cos(radians(:lat)) * cos(radians(latitude)) *
                cos(radians(longitude) - radians(:lng)) +
                sin(radians(:lat)) * sin(radians(latitude))
            )
        ) < :radiusKm
        ORDER BY distance
    """, nativeQuery = true)
    List<Vet> findVetsNearby(
        @Param("lat") Double lat,
        @Param("lng") Double lng,
        @Param("radiusKm") Double radiusKm
    );
}