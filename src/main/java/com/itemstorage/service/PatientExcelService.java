package com.itemstorage.service;

import com.itemstorage.entity.Patient;
import com.itemstorage.repository.PatientRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PatientExcelService {

    private final PatientRepository patientRepository;

    public PatientExcelService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Загрузка пациентов из Excel-файла.
     * Ожидаемые колонки: Номер истории (клиента) | ФИО | Дата рождения
     */
    @Transactional
    public Map<String, Object> importFromExcel(MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        int imported = 0;
        int skipped = 0;
        List<String> errors = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheetAt(0); // Первый лист

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Пропускаем заголовок
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    // Колонка 0: Номер истории (клиента)
                    String medicalCardNumber = getCellStringValue(row.getCell(0));
                    // Колонка 1: ФИО
                    String fullName = getCellStringValue(row.getCell(1));
                    // Колонка 2: Дата рождения
                    LocalDate birthDate = getCellDateValue(row.getCell(2));

                    if (medicalCardNumber.isEmpty() || fullName.isEmpty()) {
                        skipped++;
                        continue;
                    }

                    // Проверяем, есть ли уже такой пациент
                    if (patientRepository.findByMedicalCardNumber(medicalCardNumber).isPresent()) {
                        skipped++;
                        continue;
                    }

                    Patient patient = Patient.builder()
                            .medicalCardNumber(medicalCardNumber)
                            .fullName(fullName)
                            .birthDate(birthDate)
                            .build();

                    patientRepository.save(patient);
                    imported++;

                } catch (Exception e) {
                    errors.add("Строка " + (i + 1) + ": " + e.getMessage());
                    skipped++;
                }
            }
        } catch (Exception e) {
            result.put("error", "Ошибка чтения файла: " + e.getMessage());
            return result;
        }

        result.put("imported", imported);
        result.put("skipped", skipped);
        result.put("errors", errors);
        return result;
    }

    private String getCellStringValue(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue());
            default:
                return "";
        }
    }

    private LocalDate getCellDateValue(Cell cell) {
        if (cell == null) return null;
        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getDateCellValue()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            } else if (cell.getCellType() == CellType.STRING) {
                String dateStr = cell.getStringCellValue().trim();
                if (dateStr.isEmpty()) return null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[dd.MM.yyyy][dd/MM/yyyy][yyyy-MM-dd]");
                return LocalDate.parse(dateStr, formatter);
            }
        } catch (Exception e) {
            // Игнорируем некорректную дату
        }
        return null;
    }

    // Экспорт всех пациентов в Excel (для образца)
    public byte[] exportToExcel() {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Пациенты");

            // Заголовок
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Номер истории");
            header.createCell(1).setCellValue("ФИО");
            header.createCell(2).setCellValue("Дата рождения");

            // Данные
            List<Patient> patients = patientRepository.findAll();
            int rowNum = 1;
            for (Patient patient : patients) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(patient.getMedicalCardNumber());
                row.createCell(1).setCellValue(patient.getFullName());
                if (patient.getBirthDate() != null) {
                    row.createCell(2).setCellValue(patient.getBirthDate().toString());
                }
            }

            java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
            workbook.write(bos);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка экспорта", e);
        }
    }
}
