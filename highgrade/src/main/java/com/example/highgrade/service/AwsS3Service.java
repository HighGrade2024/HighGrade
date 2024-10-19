package com.example.highgrade.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
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
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 s3Client;

    public List<String> upload(List<MultipartFile> multipartFiles) {

        List<String> fileNames = new ArrayList<>();

        multipartFiles.forEach(multipartFile -> {
            String fileName = createFilName(multipartFile.getOriginalFilename());

            // 이미지 전처리 단계 (콘텐츠 타입, 사이즈 설정)
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());

            try(InputStream inputStream = multipartFile.getInputStream()) {
                s3Client.putObject(bucket, fileName, inputStream, objectMetadata);
                fileNames.add(fileName);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패하였습니다.");
            }
        });

        return fileNames;
    }

    public String createFilName(String fileName){
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    public String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "잘못된 형식의 파일 ("+ fileName +") 입니다."
            );

        }
    }
}
