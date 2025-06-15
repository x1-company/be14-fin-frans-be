package com.x1.frans.aws.controller;

import com.x1.frans.aws.service.AmazonS3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class AmazonS3Controller {

    private final AmazonS3Service amazonS3Service;

    public AmazonS3Controller(AmazonS3Service amazonS3Service) {
        this.amazonS3Service = amazonS3Service;
    }

    @PostMapping
    public ResponseEntity<List<String>> uploadFile(@RequestParam("files") List<MultipartFile> multipartFiles,
                                                   @RequestParam("type") String uploadType) {
        return ResponseEntity.ok(amazonS3Service.uploadFile(multipartFiles, uploadType));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam String fileName,
                                             @RequestParam("type") String uploadType){
        amazonS3Service.deleteFile(fileName, uploadType);
        return ResponseEntity.ok(fileName);
    }
}
