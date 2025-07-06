package com.example.highgrade.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AwsS3ServiceTest {

    @InjectMocks
    private AwsS3Service awsS3Service;

    @Mock
    private AmazonS3 amazonS3;

    @Test
    @DisplayName("S3 파일 업로드 테스트")
    void upload() throws IOException {
        // given
        MultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "test".getBytes());
        when(amazonS3.putObject(any())).thenReturn(new PutObjectResult());
        when(amazonS3.getUrl(any(), any())).thenReturn(new URL("http://localhost/test.txt"));

        // when
        String url = awsS3Service.upload(multipartFile);

        // then
        assertThat(url).isEqualTo("http://localhost/test.txt");
    }
}
