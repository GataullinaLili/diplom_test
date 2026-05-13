package com.itemstorage.entity;

import com.itemstorage.enums.StorageType;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "storages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "storage_type", nullable = false, length = 20)
    private StorageType storageType;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
    @Builder.Default
    private List<StorageCell> cells = new ArrayList<>();
}
