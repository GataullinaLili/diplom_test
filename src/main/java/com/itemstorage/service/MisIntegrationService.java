package com.itemstorage.service;

import com.itemstorage.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Сервис интеграции с МИС (ЕМИАС).
 * Персональные данные пациентов НЕ сохраняются в локальной БД.
 */
@Service
public class MisIntegrationService {

    @Value("${mis.api.url}")
    private String misApiUrl;

    @Value("${mis.api.token}")
    private String misApiToken;

    private final RestTemplate restTemplate;

    public MisIntegrationService() {
        this.restTemplate = new RestTemplate();
    }

    public PatientDTO findPatientByQuery(String query) {
        // Здесь реальный запрос к МИС. Возвращаем заглушку для разработки.
        // String url = misApiUrl + "/patient/search?token=" + misApiToken + "&q=" + query;
        // return restTemplate.getForObject(url, PatientDTO.class);
        
        // Заглушка:
        PatientDTO dto = new PatientDTO();
        dto.setMisId("MIS-" + query.hashCode());
        dto.setFullName(query);
        dto.setBirthDate("01.01.1980");
        dto.setMedicalCardNumber("ИБ-" + Math.abs(query.hashCode() % 10000));
        return dto;
    }

    public PatientDTO findPatientByQrCode(String qrData) {
        // Аналогично поиск по QR с браслета
        return findPatientByQuery(qrData);
    }
}
