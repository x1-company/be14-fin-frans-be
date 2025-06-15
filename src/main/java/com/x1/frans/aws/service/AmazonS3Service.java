package com.x1.frans.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.x1.frans.exception.AwsFIleUploadException;
import com.x1.frans.exception.InvalidFilUploadTypeException;
import com.x1.frans.exception.InvalidFileExtensionException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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

    public List<String> uploadFile(List<MultipartFile> multipartFiles, String uploadType){

        List<String> response = new ArrayList<>();

        // 업로드 타입에 따른 폴더 결정
        String folder = determineFolder(uploadType);

        multipartFiles.forEach(file -> {
            String uuidFileName = createFileName(file.getOriginalFilename()); // UUID 파일명
            String originalFileName = file.getOriginalFilename(); // 원본 파일명
            String key = folder + "/" + uuidFileName;

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            try(InputStream inputStream = file.getInputStream()){

                amazonS3.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata));
                String fileUrl = amazonS3.getUrl(bucket, key).toString();

                response.add(fileUrl);
                response.add(uuidFileName);
                response.add(originalFileName);

            } catch (IOException e) {
                throw new AwsFIleUploadException("파일 업로드에 실패했습니다.");
            }
        });

        return response;
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

    public void deleteFile(String fileName, String uploadType){

        String folder = determineFolder(uploadType);
        String key = folder + "/" + fileName;
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, key));

    }

    private String determineFolder(String uploadType) {

        switch (uploadType.toLowerCase()) {
            case "approval":
                return "approval";
            case "return":
                return "return";
            default:
                throw new InvalidFilUploadTypeException("지원하지 않는 업로드 타입입니다.");
        }

    }
}
