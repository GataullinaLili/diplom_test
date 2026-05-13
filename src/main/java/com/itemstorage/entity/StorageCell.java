package com.itemstorage.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "storage_cells")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class StorageCell {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;

    @Column(name = "is_occupied")
    private Boolean isOccupied = false;

    @Column(name = "qr_code_path", length = 500)
    private String qrCodePath;
}
