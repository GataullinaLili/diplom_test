package com.itemstorage.repository;

import com.itemstorage.entity.Inventory;
import com.itemstorage.enums.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByStatus(InventoryStatus status);
    
    @Query("SELECT i FROM Inventory i WHERE i.misPatientId LIKE %?1%")
    List<Inventory> findByPatientIdContaining(String patientId);
    
    List<Inventory> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT i FROM Inventory i WHERE i.cell.id = ?1 AND i.status = 'PLACED'")
    List<Inventory> findActiveByCellId(Long cellId);
}
