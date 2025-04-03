package com.deupload.deuploadBackend.repository;

import com.deupload.deuploadBackend.entities.TrashEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrashRepository extends JpaRepository<TrashEntity, Long> {
    List<TrashEntity> findByUserId(Long userId);
}