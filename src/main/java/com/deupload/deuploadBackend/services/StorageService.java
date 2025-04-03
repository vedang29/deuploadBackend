package com.deupload.deuploadBackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageService {
    public String getFileStats(Long fileId, String username) {
        // Fetch file statistics from the database
        return "File stats here.";
    }

    public String getUserStorageStats(String username) {
        // Fetch user storage stats from the database
        return "User storage stats here.";
    }
}