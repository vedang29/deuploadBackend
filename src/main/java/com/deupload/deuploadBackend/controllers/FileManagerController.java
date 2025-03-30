package com.deupload.deuploadBackend.controllers;

import com.deupload.deuploadBackend.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/")
public class FileManagerController {

    @Autowired
    private FileStorageService fileStorageService;
    private static final Logger log = Logger.getLogger(FileManagerController.class.getName());

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/upload-file")
    public boolean uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        try {
            fileStorageService.saveFile(file);
            return true;
        } catch (IOException e) {
            log.log(Level.SEVERE,"Exception during upload",e);
        }
        return false;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("fileName") String filename) throws Exception {
        try {
            var fileToDownload = fileStorageService.getDownloadFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"")
                    .contentLength(fileToDownload.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(Files.newInputStream(fileToDownload.toPath())));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/download-faster")
    public ResponseEntity<Resource> downloadFileFaster(@RequestParam("fileName") String filename) throws Exception {
        try {
            var fileToDownload = fileStorageService.getDownloadFile(filename);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + filename + "\"")
                    .contentLength(fileToDownload.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new FileSystemResource(fileToDownload));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/list-files")
    public ResponseEntity<List<String>> listFiles() {
        try {
            List<String> fileNames = fileStorageService.listAllFiles();
            return ResponseEntity.ok(fileNames);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Exception during listing files", e);
            return ResponseEntity.internalServerError().build();
        }
    }


//
//    @GetMapping("/list-files")
//    public String listFiles(Model model) throws IOException{
//        try (DirectoryStream<Path> stream = Files.newDirectoryStream(new File(FileStorageService.STORAGE_DIRECTORY).toPath())){
//            model.addAttribute("files",
//                    StreamSupport.stream(stream.spliterator(),false)
//                            .map(Path::getFileName)
//                            .map(Path::toString)
//                            .collect(Collectors.toList())
//                    );
//        }
//        return "list_files";
//    }
}
