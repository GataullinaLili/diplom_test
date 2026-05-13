package com.itemstorage.dto;

import lombok.Data;
import java.util.List;

@Data
public class InventoryRequest {
    private String misPatientId;
    private String patientBirthDate;
    private List<ItemRequest> items;
}
