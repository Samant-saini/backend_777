package com.example.project.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.project.entity.AnimalReport;

import java.util.List;

@Repository
public interface AnimalReportRepository extends JpaRepository<AnimalReport,Long> {
    List<AnimalReport> findByReportedByEmail(String email);
    List<AnimalReport> findByStatus(AnimalReport.ReportStatus status);
    
}
