package com.itemstorage.controller;

import com.itemstorage.entity.AuditLog;
import com.itemstorage.service.AuditService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/audit")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping
    public String auditPage() {
        return "audit";
    }

    @PostMapping("/search")
    public String search(@RequestParam(required = false) String patientFio,
                        @RequestParam(required = false) String employeeFio,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                        Model model) {
        List<AuditLog> logs = auditService.search(patientFio, employeeFio, startDate, endDate);
        model.addAttribute("logs", logs);
        return "audit-results";
    }
}
