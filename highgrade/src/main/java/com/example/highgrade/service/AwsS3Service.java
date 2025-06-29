package com.example.highgrade.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 s3Client;

    /**
     * S3 이미지 업로드 ( 리스트 )
     * @param multipartFile
     * @return
     */
    public String upload(MultipartFile multipartFile) {

        String fileName = createFilName(multipartFile.getOriginalFilename());

        // 이미지 전처리 단계 (콘텐츠 타입, 사이즈 설정)
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            //이미지 S3에 업로드 시 ACL 권한을 PublicRead로 설정하여 모든 사용자에게 읽기 권한을 부여
            s3Client.putObject(
                    new PutObjectRequest(
                            bucket,
                            fileName,
                            inputStream,
                            objectMetadata
                    ).withCannedAcl(CannedAccessControlList.PublicRead)
            );

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패하였습니다.");
        }


        return s3Client.getUrl(bucket, fileName).toString();
    }

    /**
     * Image 삭제
     * @param fileName
     */
    public void deleteImage(String fileName) {
        s3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
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
