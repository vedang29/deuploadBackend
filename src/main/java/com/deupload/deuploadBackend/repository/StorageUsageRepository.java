package com.deupload.deuploadBackend.repository;

import com.deupload.deuploadBackend.entities.StorageUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageUsageRepository extends JpaRepository<StorageUsage, Long> {
    StorageUsage findByUserId(Long userId);
}