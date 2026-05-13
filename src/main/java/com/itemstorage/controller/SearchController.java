package com.itemstorage.controller;

import com.itemstorage.dto.PatientDTO;
import com.itemstorage.service.MisIntegrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final MisIntegrationService misService;

    public SearchController(MisIntegrationService misService) {
        this.misService = misService;
    }

    @GetMapping
    public String searchPage() {
        return "search";
    }

    @PostMapping
    public String search(@RequestParam String query, Model model) {
        PatientDTO patient = misService.findPatientByQuery(query);
        model.addAttribute("patient", patient);
        return "search-results";
    }
}
