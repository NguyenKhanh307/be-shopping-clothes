package com.khanhdev.be_shopping_clothes.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

@Service
public class FileStorageService {

    @Value("${FE_IMAGE_DIR}")
    private String feImageDir;

    // Danh sách các loại file ảnh cho phép
    private final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/webp");

    public String storeFile(MultipartFile file) {
        // 1. Kiểm tra file có rỗng không
        if (file.isEmpty()) {
            throw new RuntimeException("Vui lòng chọn một file ảnh!");
        }

        // 2. Validate định dạng file (Chỉ nhận image/...)
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType.toLowerCase())) {
            throw new RuntimeException("Định dạng file không hợp lệ! Chỉ chấp nhận JPG, PNG, GIF, WEBP.");
        }

        try {
            Path root = Paths.get(feImageDir).toAbsolutePath().normalize();
            if (!Files.exists(root)) Files.createDirectories(root);

            String fileName = file.getOriginalFilename();
            Path targetLocation = root.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi lưu ảnh: " + e.getMessage());
        }
    }
}