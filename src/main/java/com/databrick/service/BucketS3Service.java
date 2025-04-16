package com.databrick.service;

import com.databrick.config.S3Provider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class BucketS3Service {

    S3Client s3Client = new S3Provider().getS3Client();
    String bucketName;

    public void createBucket() {
        try {
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3Client.createBucket(createBucketRequest);
            System.out.println("Bucket criado com sucesso: " + bucketName);
        } catch (S3Exception e) {
            System.err.println("Erro ao criar o bucket: " + e.getMessage());
        }
    }

}
