package com.deupload.deuploadBackend.services;

import com.deupload.deuploadBackend.dto.FileDTO;
import com.deupload.deuploadBackend.utils.EncryptionHelper;
import lombok.RequiredArgsConstructor;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileSystem hdfs;
    private final EncryptionHelper encryptionHelper;

    // Upload a file to HDFS
    public String uploadFile(MultipartFile file, String username) {
        try {
            // Encrypt file before storing in HDFS
            byte[] encryptedData = encryptionHelper.encryptFile(file.getBytes(), encryptionHelper.getPublicKey());
            Path hdfsPath = new Path("/user/" + username + "/" + file.getOriginalFilename());

            if (hdfs.exists(hdfsPath)) {
                return "File already exists!";
            }

            try (FSDataOutputStream outputStream = hdfs.create(hdfsPath)) {
                outputStream.write(encryptedData);
            }

            return "File uploaded successfully!";
        } catch (Exception e) {
            return "Error uploading file: " + e.getMessage();
        }
    }

    // Fetch all files of a user
    public List<FileDTO> getAllFiles(String username) {
        // Logic to retrieve files from DB
        return List.of(); // Placeholder for database interaction
    }

    // Get details of a specific file
    public FileDTO getFileDetails(Long fileId, String username) {
        // Logic to retrieve file details from DB
        return new FileDTO(); // Placeholder for database interaction
    }

    // Download a file from HDFS (decrypt before sending)
    public ResponseEntity<byte[]> downloadFile(Long fileId, String username) {
        try {
            Path hdfsPath = new Path("/user/" + username + "/" + fileId);

            if (!hdfs.exists(hdfsPath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] encryptedData;
            try (FSDataInputStream inputStream = hdfs.open(hdfsPath)) {
                encryptedData = inputStream.readAllBytes();
            }

            byte[] decryptedData = encryptionHelper.decryptFile(encryptedData, encryptionHelper.getPrivateKey());

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + fileId + "\"")
                    .body(decryptedData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // Move a file to trash (soft delete)
    public String moveToTrash(Long fileId, String username) {
        // Implement logic to update the is_deleted flag in the database
        return "File moved to trash.";
    }

    // Star or unstar a file
    public String toggleStar(Long fileId, String username) {
        // Implement logic to toggle star status in the database
        return "File star status updated.";
    }

    // Delete a file from HDFS
    public String deleteFile(Long fileId, String username) {
        try {
            Path hdfsPath = new Path("/user/" + username + "/" + fileId);

            if (hdfs.exists(hdfsPath)) {
                hdfs.delete(hdfsPath, false);
                return "File deleted successfully!";
            } else {
                return "File not found!";
            }
        } catch (Exception e) {
            return "Error deleting file: " + e.getMessage();
        }
    }
}
//package com.deupload.deuploadBackend.services;
//
//import com.deupload.deuploadBackend.dto.FileDTO;
//import com.deupload.deuploadBackend.utils.EncryptionHelper;
//import lombok.RequiredArgsConstructor;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class FileService {
//    private final FileSystem hdfs;
//    private final EncryptionHelper encryptionHelper;
//
//    public String uploadFile(MultipartFile file, String username) {
//        try {
//            byte[] encryptedData = encryptionHelper.encryptFile(file.getBytes(), encryptionHelper.getPublicKey());
//            Path hdfsPath = new Path("/user/" + username + "/" + file.getOriginalFilename());
//
//            if (hdfs.exists(hdfsPath)) {
//                return "File already exists!";
//            }
//
//            FSDataOutputStream outputStream = hdfs.create(hdfsPath);
//            outputStream.write(encryptedData);
//            outputStream.close();
//
//            return "File uploaded successfully!";
//        } catch (Exception e) {
//            return "Error uploading file: " + e.getMessage();
//        }
//    }
//
//    public List<FileDTO> getAllFiles(String username) {
//        return List.of(); // Implement DB query
//    }
//
//    public FileDTO getFileDetails(Long fileId, String username) {
//        return new FileDTO(); // Implement DB query
//    }
//
//    public ResponseEntity<byte[]> downloadFile(Long fileId, String username) {
//        try {
//            Path hdfsPath = new Path("/user/" + username + "/" + fileId);
//            if (!hdfs.exists(hdfsPath)) {
//                return ResponseEntity.notFound().build();
//            }
//
//            FSDataInputStream inputStream = hdfs.open(hdfsPath);
//            byte[] encryptedData = inputStream.readAllBytes();
//            inputStream.close();
//
//            byte[] decryptedData = encryptionHelper.decryptFile(encryptedData, encryptionHelper.getPrivateKey());
//
//            return ResponseEntity.ok()
//                    .header("Content-Disposition", "attachment; filename=\"" + fileId + "\"")
//                    .body(decryptedData);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(null);
//        }
//    }
//
//    public String moveToTrash(Long fileId, String username) {
//        return "File moved to trash.";
//    }
//
//    public String toggleStar(Long fileId, String username) {
//        return "File star status updated.";
//    }
//
//    public String deleteFile(Long fileId, String username) {
//        try {
//            Path hdfsPath = new Path("/user/" + username + "/" + fileId);
//            if (hdfs.exists(hdfsPath)) {
//                hdfs.delete(hdfsPath, false);
//                return "File deleted successfully!";
//            } else {
//                return "File not found!";
//            }
//        } catch (Exception e) {
//            return "Error deleting file: " + e.getMessage();
//        }
//    }
//}
