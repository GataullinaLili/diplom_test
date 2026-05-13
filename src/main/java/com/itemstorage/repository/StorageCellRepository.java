package com.itemstorage.repository;

import com.itemstorage.entity.StorageCell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface StorageCellRepository extends JpaRepository<StorageCell, Long> {
    List<StorageCell> findByStorageId(Long storageId);
    
    @Query("SELECT c FROM StorageCell c WHERE c.storage.id = ?1 AND c.isOccupied = false")
    List<StorageCell> findFreeByStorageId(Long storageId);
}
