package com.itemstorage.dto;

import com.itemstorage.enums.StorageType;
import lombok.Data;

@Data
public class StorageRequest {
    private String name;
    private StorageType storageType;
}
