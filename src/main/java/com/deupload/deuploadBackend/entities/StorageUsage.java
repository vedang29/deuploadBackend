package com.deupload.deuploadBackend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "storage_usage")
public class StorageUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer totalFiles;

    @Column(nullable = false)
    private Long usedSpace;
}