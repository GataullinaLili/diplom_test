package com.itemstorage.service;

import com.itemstorage.entity.AuditLog;
import com.itemstorage.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    public void log(Long inventoryId, String patientFio, String action, 
                    String performedBy, String details) {
        AuditLog log = AuditLog.builder()
                .inventoryId(inventoryId)
                .patientFio(patientFio)
                .action(action)
                .performedBy(performedBy)
                .performedAt(LocalDateTime.now())
                .details(details)
                .build();
        auditLogRepository.save(log);
    }

    public List<AuditLog> search(String patientFio, String employeeFio,
                                  LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.search(patientFio, employeeFio, startDate, endDate);
    }

    public List<AuditLog> getInventoryHistory(Long inventoryId) {
        return auditLogRepository.findByInventoryIdOrderByPerformedAtDesc(inventoryId);
    }
}
