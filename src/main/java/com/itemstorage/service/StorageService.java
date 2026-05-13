package com.itemstorage.service;

import com.itemstorage.dto.StorageRequest;
import com.itemstorage.entity.Storage;
import com.itemstorage.entity.StorageCell;
import com.itemstorage.enums.StorageType;
import com.itemstorage.repository.StorageCellRepository;
import com.itemstorage.repository.StorageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StorageService {

    private final StorageRepository storageRepository;
    private final StorageCellRepository cellRepository;

    public StorageService(StorageRepository storageRepository,
                          StorageCellRepository cellRepository) {
        this.storageRepository = storageRepository;
        this.cellRepository = cellRepository;
    }

    public List<Storage> getAllStorages() {
        return storageRepository.findAll();
    }

    @Transactional
    public Storage createStorage(StorageRequest request) {
        Storage storage = Storage.builder()
                .name(request.getName())
                .storageType(request.getStorageType())
                .build();
        return storageRepository.save(storage);
    }

    @Transactional
    public StorageCell createCell(String name, Long storageId) {
        Storage storage = storageRepository.findById(storageId)
                .orElseThrow(() -> new RuntimeException("Склад не найден"));

        if (storage.getStorageType() == StorageType.RECEPTION) {
            throw new RuntimeException("Нельзя создавать ячейки на складе приёмного отделения");
        }

        StorageCell cell = StorageCell.builder()
                .name(name)
                .storage(storage)
                .isOccupied(false)
                .build();
        return cellRepository.save(cell);
    }

    public List<StorageCell> getFreeCells(Long storageId) {
        return cellRepository.findFreeByStorageId(storageId);
    }

    public List<StorageCell> getCellsByStorage(Long storageId) {
        return cellRepository.findByStorageId(storageId);
    }
}
