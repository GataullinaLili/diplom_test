package com.itemstorage.controller;

import com.itemstorage.dto.InventoryRequest;
import com.itemstorage.dto.PatientDTO;
import com.itemstorage.entity.Inventory;
import com.itemstorage.service.InventoryService;
import com.itemstorage.service.MisIntegrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/receptionist")
public class ReceptionistController {

    private final InventoryService inventoryService;
    private final MisIntegrationService misService;

    public ReceptionistController(InventoryService inventoryService, MisIntegrationService misService) {
        this.inventoryService = inventoryService;
        this.misService = misService;
    }

    @GetMapping("/create")
    public String createInventoryPage() {
        return "receptionist/create-inventory";
    }

    @PostMapping("/create")
    public String createInventory(@ModelAttribute InventoryRequest request) {
        PatientDTO patient = misService.findPatientByQuery(request.getMisPatientId());
        inventoryService.createInventory(request, /*getCurrentUser()*/ null, patient.getFullName());
        return "redirect:/inventories";
    }
}
