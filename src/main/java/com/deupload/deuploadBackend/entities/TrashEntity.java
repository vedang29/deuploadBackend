package com.deupload.deuploadBackend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "trash")
public class TrashEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "file_id")
    private FileEntity file;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime deletedAt;
}