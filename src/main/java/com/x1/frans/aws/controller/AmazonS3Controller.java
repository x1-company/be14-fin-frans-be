package com.x1.frans.aws.controller;

import com.x1.frans.aws.service.AmazonS3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file")
public class AmazonS3Controller {

    private final AmazonS3Service amazonS3Service;

    public AmazonS3Controller(AmazonS3Service amazonS3Service) {
        this.amazonS3Service = amazonS3Service;
    }

    @PostMapping
    public ResponseEntity<List<String>> uploadFile(@RequestParam("files") List<MultipartFile> multipartFiles){
        return ResponseEntity.ok(amazonS3Service.uploadFile(multipartFiles));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam String fileName){
        amazonS3Service.deleteFile(fileName);
        return ResponseEntity.ok(fileName);
    }
}
