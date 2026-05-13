package com.itemstorage.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "medical_card_number", nullable = false, unique = true, length = 50)
    private String medicalCardNumber;  // Номер истории болезни (уникальный)

    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;           // ФИО пациента

    @Column(name = "birth_date")
    private LocalDate birthDate;       // Дата рождения

    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();
}
