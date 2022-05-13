package com.rissins.records.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rissins.records.utils.enctypt.SHA_256;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final SHA_256 sha256;
    private final EventService eventService;
    private AmazonS3 s3Client;

    @Value("${aws.s3.accessKey}")
    private String accessKey;

    @Value("${aws.s3.secretKey}")
    private String secretKey;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.region}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }

    public String upload(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String fileName = file.getOriginalFilename();
        long currentTimeMillis = System.currentTimeMillis();
        String[] splitData = fileName.split("\\.");
        String fileType = splitData[(splitData.length) - 1];
        String encryptFileName = sha256.encryptBySha256(fileName + currentTimeMillis) + "." + fileType;

        s3Client.putObject(new PutObjectRequest(bucket, encryptFileName, file.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.Private));
        return s3Client.getUrl(bucket, encryptFileName).toString();
    }

    public void delete(Long id) {
        String fileById = eventService.findFileById(id);
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, fileById);
        s3Client.deleteObject(request);
    }
}
