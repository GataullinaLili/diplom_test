package com.itemstorage.repository;

import com.itemstorage.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    @Query("SELECT a FROM AuditLog a WHERE " +
           "(:patientFio IS NULL OR LOWER(a.patientFio) LIKE LOWER(CONCAT('%', :patientFio, '%'))) AND " +
           "(:employeeFio IS NULL OR LOWER(a.performedBy) LIKE LOWER(CONCAT('%', :employeeFio, '%'))) AND " +
           "(:startDate IS NULL OR a.performedAt >= :startDate) AND " +
           "(:endDate IS NULL OR a.performedAt <= :endDate) " +
           "ORDER BY a.performedAt DESC")
    List<AuditLog> search(String patientFio, String employeeFio, 
                          LocalDateTime startDate, LocalDateTime endDate);
    
    List<AuditLog> findByInventoryIdOrderByPerformedAtDesc(Long inventoryId);
}
