package com.ffcontrol.fast_food_control.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
    private final Path storageLocation;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) throws IOException {
        this.storageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.storageLocation);
    }

    public String storeFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path targetLocation = this.storageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation);

        return "/uploads/" + fileName;
    }
}
