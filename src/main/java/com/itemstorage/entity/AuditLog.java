package com.itemstorage.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inventory_id")
    private Long inventoryId;

    @Column(name = "patient_fio", length = 200)
    private String patientFio;

    @Column(nullable = false, length = 100)
    private String action;

    @Column(name = "performed_by", nullable = false, length = 150)
    private String performedBy;

    @Column(name = "performed_at")
    private LocalDateTime performedAt = LocalDateTime.now();

    @Column(length = 500)
    private String details;
}
