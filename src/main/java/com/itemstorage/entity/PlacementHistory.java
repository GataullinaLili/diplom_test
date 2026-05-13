package com.itemstorage.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "placement_history")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PlacementHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cell_id")
    private StorageCell cell;

    @Column(nullable = false, length = 50)
    private String action; // PLACED, MOVED, ISSUED

    @Column(name = "performed_by", nullable = false, length = 150)
    private String performedBy;

    @Column(name = "performed_at")
    private LocalDateTime performedAt = LocalDateTime.now();

    @Column(name = "previous_cell", length = 50)
    private String previousCell;

    @Column(name = "new_cell", length = 50)
    private String newCell;
}
