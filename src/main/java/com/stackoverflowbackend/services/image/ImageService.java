package com.stackoverflowbackend.services.image;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void storeFile(MultipartFile multipartFile, Long answerId);
}
