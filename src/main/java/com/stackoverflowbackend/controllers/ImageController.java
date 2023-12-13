package com.stackoverflowbackend.controllers;

import com.stackoverflowbackend.services.image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.endpoint.base-url}/images")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/{answerId}")
    public String uploadFile(@RequestParam MultipartFile multipartFile, @PathVariable Long answerId) {
        imageService.storeFile(multipartFile, answerId);
        return "Image store successfully";
    }
}
