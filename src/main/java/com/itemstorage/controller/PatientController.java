package com.itemstorage.controller;

import com.itemstorage.service.PatientExcelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientExcelService patientExcelService;

    public PatientController(PatientExcelService patientExcelService) {
        this.patientExcelService = patientExcelService;
    }

    @GetMapping
    public String patientsPage() {
        return "patients";
    }

    @PostMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file, Model model) {
        Map<String, Object> result = patientExcelService.importFromExcel(file);
        model.addAttribute("result", result);
        return "patients";
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportExcel() {
        byte[] data = patientExcelService.exportToExcel();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        "attachment; filename=patients.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }
}
