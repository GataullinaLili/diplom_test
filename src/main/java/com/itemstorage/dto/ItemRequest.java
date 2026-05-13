package com.itemstorage.dto;

import lombok.Data;

@Data
public class ItemRequest {
    private String name;
    private Integer quantity;
    private String description;
}
