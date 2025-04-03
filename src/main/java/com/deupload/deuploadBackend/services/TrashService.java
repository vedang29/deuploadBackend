package com.deupload.deuploadBackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrashService {
    public List<String> getTrashFiles(String username) {
        // Retrieve trash files from the database
        return List.of();
    }

    public String restoreFile(Long fileId, String username) {
        // Restore file from trash
        return "File restored.";
    }

    public String permanentlyDeleteFile(Long fileId, String username) {
        // Delete file from HDFS permanently
        return "File permanently deleted.";
    }
}