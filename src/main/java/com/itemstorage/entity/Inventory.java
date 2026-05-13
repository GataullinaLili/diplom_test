package com.itemstorage.entity;

import com.itemstorage.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "inventories")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ID пациента в МИС (персональные данные НЕ храним)
    @Column(name = "mis_patient_id", nullable = false, length = 50)
    private String misPatientId;

    // Кешируем ФИО только на время активной сессии (можно очищать)
    @Column(name = "patient_fio_display", length = 200)
    private String patientFioDisplay;

    @Column(name = "patient_birth_date", length = 10)
    private String patientBirthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InventoryStatus status = InventoryStatus.CREATED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cell_id")
    private StorageCell cell;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "issued_by")
    private String issuedBy;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Item> items = new ArrayList<>();
}
