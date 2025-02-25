package com.deupload.deuploadBackend.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileStorageService {
    public static final String STORAGE_DIRECTORY = "E:\\Projects\\deuploadBackend\\Storage";
    private final Path storageLocation = Paths.get(STORAGE_DIRECTORY);

    public void saveFile(MultipartFile fileToSave) throws IOException {
        if(fileToSave == null){
            throw new NullPointerException("fileToSave is null");
        }
        var targetFile = new File(STORAGE_DIRECTORY + File.separator +fileToSave.getOriginalFilename());
        if(!Objects.equals(targetFile.getParent(),STORAGE_DIRECTORY)){
            throw new SecurityException("Unsupported filename!");
        }
        Files.copy(fileToSave.getInputStream(),targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public File getDownloadFile(String fileName) throws Exception{
        if(fileName == null){
            throw new NullPointerException("fileToSave is null");
        }
        var fileToDownload = new File(STORAGE_DIRECTORY + File.separator + fileName);
        if(!Objects.equals(fileToDownload.getParent(),STORAGE_DIRECTORY)){
            throw new SecurityException("Unsupported filename!");
        }
        if(!fileToDownload.exists()){
            throw new FileNotFoundException("No file named: "+ fileName);
        }
        return fileToDownload;
    }

    public List<String> listAllFiles() throws IOException {
        try (Stream<Path> paths = Files.walk(storageLocation, 1)) {
            return paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());
        }
    }
}
