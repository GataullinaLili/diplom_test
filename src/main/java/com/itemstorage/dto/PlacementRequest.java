package com.itemstorage.dto;

import lombok.Data;

@Data
public class PlacementRequest {
    private Long inventoryId;
    private Long cellId;
}
