package com.deupload.deuploadBackend.repository;

import com.deupload.deuploadBackend.entities.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByUserId(Long userId);
}