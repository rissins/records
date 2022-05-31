package com.rissins.records.service;

import com.amazonaws.services.s3.model.S3Object;
import com.rissins.records.S3Fixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class S3ServiceTest {

    @Autowired
    S3Service s3Service;

    private S3Fixtures s3Fixtures;
    private String encryptFileName;


    @BeforeEach
    void setUp() {
        this.s3Fixtures = new S3Fixtures();
    }

    @Order(1)
    @Test
    void 파일_생성() throws IOException, NoSuchAlgorithmException {
        //given
        MultipartFile image = s3Fixtures.getImage();

        //when
        String upload = s3Service.upload(image);
        String[] split = upload.split("/");
        encryptFileName = split[split.length - 1];

        //then
        Assertions.assertThat(upload).isNotBlank();
    }

    @Order(2)
    @Test
    void 파일_삭제_실패() {
        //given
        String deleteFileNameWithIncorrectTestFileName = encryptFileName + "11";

        //when
        s3Service.delete(deleteFileNameWithIncorrectTestFileName);
        S3Object objectByFileName = s3Service.findObjectByFileName(encryptFileName);

        //then
        Assertions.assertThat(objectByFileName).isNotNull();
    }

    @Order(3)
    @Test
    void 파일_삭제_성공() {
        //given

        //when
        s3Service.delete(encryptFileName);
        S3Object objectByFileName = s3Service.findObjectByFileName(encryptFileName);

        //then
        Assertions.assertThat(objectByFileName).isNull();
    }
}