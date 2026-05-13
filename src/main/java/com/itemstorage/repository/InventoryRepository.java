package com.itemstorage.repository;

import com.itemstorage.entity.Inventory;
import com.itemstorage.enums.InventoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    
    List<Inventory> findByStatus(InventoryStatus status);
    
    // Поиск описей по ID пациента
    List<Inventory> findByPatientId(Long patientId);
    
    // Поиск описей по ФИО пациента
    @Query("SELECT i FROM Inventory i WHERE LOWER(i.patient.fullName) LIKE LOWER(CONCAT('%', :fio, '%'))")
    List<Inventory> findByPatientFullNameContaining(String fio);
    
    // Поиск описей по номеру медкарты
    @Query("SELECT i FROM Inventory i WHERE i.patient.medicalCardNumber LIKE CONCAT('%', :cardNumber, '%')")
    List<Inventory> findByPatientCardNumberContaining(String cardNumber);
    
    List<Inventory> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    
    @Query("SELECT i FROM Inventory i WHERE i.cell.id = ?1 AND i.status = 'PLACED'")
    List<Inventory> findActiveByCellId(Long cellId);
}
