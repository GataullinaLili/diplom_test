package com.itemstorage.dto;

import lombok.Data;
import java.util.List;

@Data
public class InventoryRequest {
    private String medicalCardNumber;  // Номер истории болезни (вместо misPatientId)
    private List<ItemRequest> items;
}
