package com.itemstorage.controller;

import com.itemstorage.dto.PlacementRequest;
import com.itemstorage.entity.Inventory;
import com.itemstorage.entity.User;
import com.itemstorage.service.InventoryService;
import com.itemstorage.service.StorageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/storekeeper")
public class StorekeeperController {

    private final InventoryService inventoryService;
    private final StorageService storageService;

    public StorekeeperController(InventoryService inventoryService, StorageService storageService) {
        this.inventoryService = inventoryService;
        this.storageService = storageService;
    }

    @GetMapping("/placement")
    public String placementPage(Model model) {
        model.addAttribute("inventories", inventoryService.getActiveInventories());
        model.addAttribute("storages", storageService.getAllStorages());
        return "storekeeper/placement";
    }

    @PostMapping("/place")
    public String placeInventory(@ModelAttribute PlacementRequest request,
                                  @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        // Получаем нашего User из БД (в реальном коде через сервис)
        User user = getUserFromPrincipal(principal);
        inventoryService.placeToStorage(request.getInventoryId(), request.getCellId(), user);
        return "redirect:/storekeeper/placement";
    }

    @PostMapping("/issue/{id}")
    public String issueInventory(@PathVariable Long id,
                                  @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal) {
        User user = getUserFromPrincipal(principal);
        inventoryService.issueInventory(id, user);
        return "redirect:/inventories";
    }

    private User getUserFromPrincipal(org.springframework.security.core.userdetails.User principal) {
        // Упрощённо; в реальном коде — через UserRepository
        return null; // Заглушка
    }
}
