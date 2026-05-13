package com.itemstorage.service;

import com.itemstorage.dto.InventoryRequest;
import com.itemstorage.dto.ItemRequest;
import com.itemstorage.entity.*;
import com.itemstorage.enums.InventoryStatus;
import com.itemstorage.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final StorageCellRepository cellRepository;
    private final AuditService auditService;

    public InventoryService(InventoryRepository inventoryRepository,
                            StorageCellRepository cellRepository,
                            AuditService auditService) {
        this.inventoryRepository = inventoryRepository;
        this.cellRepository = cellRepository;
        this.auditService = auditService;
    }

    @Transactional
    public Inventory createInventory(InventoryRequest request, User createdBy, String patientFio) {
        Inventory inventory = Inventory.builder()
                .misPatientId(request.getMisPatientId())
                .patientFioDisplay(patientFio)
                .patientBirthDate(request.getPatientBirthDate())
                .status(InventoryStatus.CREATED)
                .createdBy(createdBy)
                .createdAt(LocalDateTime.now())
                .build();

        for (ItemRequest itemReq : request.getItems()) {
            Item item = Item.builder()
                    .name(itemReq.getName())
                    .quantity(itemReq.getQuantity())
                    .description(itemReq.getDescription())
                    .inventory(inventory)
                    .build();
            inventory.getItems().add(item);
        }

        Inventory saved = inventoryRepository.save(inventory);
        
        auditService.log(saved.getId(), patientFio, "СОЗДАНИЕ_ОПИСИ", 
                        createdBy.getFullName(), "Создана опись №" + saved.getId());
        
        return saved;
    }

    @Transactional
    public Inventory placeToStorage(Long inventoryId, Long cellId, User performedBy) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Опись не найдена"));

        StorageCell cell = cellRepository.findById(cellId)
                .orElseThrow(() -> new RuntimeException("Ячейка не найдена"));

        String previousLocation = inventory.getCell() != null ? 
                "Склад: " + inventory.getStorage().getName() + ", Ячейка: " + inventory.getCell().getName() : 
                "Не размещено";

        inventory.setStorage(cell.getStorage());
        inventory.setCell(cell);
        inventory.setStatus(InventoryStatus.PLACED);
        cell.setIsOccupied(true);
        
        cellRepository.save(cell);
        Inventory saved = inventoryRepository.save(inventory);

        String newLocation = "Склад: " + cell.getStorage().getName() + ", Ячейка: " + cell.getName();
        
        auditService.log(saved.getId(), saved.getPatientFioDisplay(), "РАЗМЕЩЕНИЕ", 
                        performedBy.getFullName(), 
                        "Перемещено: " + previousLocation + " -> " + newLocation);

        return saved;
    }

    @Transactional
    public Inventory issueInventory(Long inventoryId, User performedBy) {
        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Опись не найдена"));

        // Освобождаем ячейку
        if (inventory.getCell() != null) {
            StorageCell cell = inventory.getCell();
            cell.setIsOccupied(false);
            cellRepository.save(cell);
        }

        inventory.setStatus(InventoryStatus.ISSUED);
        inventory.setIssuedAt(LocalDateTime.now());
        inventory.setIssuedBy(performedBy.getFullName());
        
        Inventory saved = inventoryRepository.save(inventory);

        auditService.log(saved.getId(), saved.getPatientFioDisplay(), "ВЫДАЧА", 
                        performedBy.getFullName(), "Вещи выданы пациенту");

        return saved;
    }

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public List<Inventory> getActiveInventories() {
        return inventoryRepository.findByStatus(InventoryStatus.PLACED);
    }
}
