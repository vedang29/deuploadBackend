package com.deupload.deuploadBackend.controllers;

import com.deupload.deuploadBackend.dto.FileDTO;
import com.deupload.deuploadBackend.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, Principal principal) {
        return ResponseEntity.ok(fileService.uploadFile(file, principal.getName()));
    }

    @GetMapping
    public ResponseEntity<List<FileDTO>> getAllFiles(Principal principal) {
        return ResponseEntity.ok(fileService.getAllFiles(principal.getName()));
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileDTO> getFileDetails(@PathVariable Long fileId, Principal principal) {
        return ResponseEntity.ok(fileService.getFileDetails(fileId, principal.getName()));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long fileId, Principal principal) {
        return fileService.downloadFile(fileId, principal.getName());
    }

    @PutMapping("/trash/{fileId}")
    public ResponseEntity<String> moveToTrash(@PathVariable Long fileId, Principal principal) {
        return ResponseEntity.ok(fileService.moveToTrash(fileId, principal.getName()));
    }

    @PutMapping("/star/{fileId}")
    public ResponseEntity<String> toggleStar(@PathVariable Long fileId, Principal principal) {
        return ResponseEntity.ok(fileService.toggleStar(fileId, principal.getName()));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable Long fileId, Principal principal) {
        return ResponseEntity.ok(fileService.deleteFile(fileId, principal.getName()));
    }
}