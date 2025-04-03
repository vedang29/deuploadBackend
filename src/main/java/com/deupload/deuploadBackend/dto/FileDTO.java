package com.deupload.deuploadBackend.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FileDTO {
    private Long id;
    private String fileName;
    private Long fileSize;
    private Boolean isStarred;
    private LocalDateTime uploadedAt;
}
