package com.x1.frans.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.x1.frans.exception.AwsFIleUploadException;
import com.x1.frans.exception.InvalidFileExtensionException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AmazonS3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public List<String> uploadFile(List<MultipartFile> multipartFiles){

        List<String> fileNameList = new ArrayList<>();

        multipartFiles.forEach(file -> {
            String fileName = createFileName(file.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()){
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
            } catch (IOException e) {
                throw new AwsFIleUploadException("파일 업로드에 실패했습니다.");
            }

            fileNameList.add(fileName);

        });

        return fileNameList;
    }

    // 파일명을 고유한 식별자 (UUID) 로 생성
    public String createFileName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    // 파일 확장자 추출 (.png, .jpg 등)
    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e){
            throw new InvalidFileExtensionException("잘못된 형식의 파일입니다.");
        }
    }

    public void deleteFile(String fileName){
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        System.out.println(bucket);
    }
}
