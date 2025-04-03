package com.deupload.deuploadBackend.controllers;

import com.deupload.deuploadBackend.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {
    private final StorageService storageService;

    @GetMapping("/stats/{fileId}")
    public ResponseEntity<String> getFileStats(@PathVariable Long fileId, Principal principal) {
        return ResponseEntity.ok(storageService.getFileStats(fileId, principal.getName()));
    }

    @GetMapping("/user")
    public ResponseEntity<String> getUserStorageStats(Principal principal) {
        return ResponseEntity.ok(storageService.getUserStorageStats(principal.getName()));
    }
}