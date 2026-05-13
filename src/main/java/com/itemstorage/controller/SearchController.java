package com.itemstorage.controller;

import com.itemstorage.entity.Patient;
import com.itemstorage.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/search")
public class SearchController {

    private final PatientRepository patientRepository;

    public SearchController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @GetMapping
    public String searchPage() {
        return "search";
    }

    @GetMapping("/api")
    @ResponseBody
    public List<Patient> searchApi(@RequestParam String query) {
        // Ищем по ФИО
        List<Patient> byFio = patientRepository.findByFullNameContaining(query);
        if (!byFio.isEmpty()) return byFio;

        // Ищем по номеру медкарты
        List<Patient> byCard = patientRepository.findByMedicalCardNumberContaining(query);
        if (!byCard.isEmpty()) return byCard;

        // Ищем по дате рождения + части ФИО (если формат "Иванов 01.01.1980")
        String[] parts = query.split(" ");
        if (parts.length >= 2) {
            try {
                String fio = parts[0];
                java.time.LocalDate birthDate = java.time.LocalDate.parse(parts[parts.length - 1],
                        java.time.format.DateTimeFormatter.ofPattern("[dd.MM.yyyy][yyyy-MM-dd]"));
                return patientRepository.findByFullNameAndBirthDate(fio, birthDate);
            } catch (Exception e) {
                // Неверный формат даты
            }
        }

        return List.of();
    }
}
