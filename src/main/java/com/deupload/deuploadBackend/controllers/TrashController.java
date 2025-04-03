package com.deupload.deuploadBackend.controllers;

import com.deupload.deuploadBackend.services.TrashService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/trash")
@RequiredArgsConstructor
public class TrashController {
    private final TrashService trashService;

    @GetMapping
    public ResponseEntity<List<String>> getTrashFiles(Principal principal) {
        return ResponseEntity.ok(trashService.getTrashFiles(principal.getName()));
    }

    @PutMapping("/restore/{fileId}")
    public ResponseEntity<String> restoreFile(@PathVariable Long fileId, Principal principal) {
        return ResponseEntity.ok(trashService.restoreFile(fileId, principal.getName()));
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> permanentlyDeleteFile(@PathVariable Long fileId, Principal principal) {
        return ResponseEntity.ok(trashService.permanentlyDeleteFile(fileId, principal.getName()));
    }
}