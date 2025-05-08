package com.databrick.config;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;


public class S3Provider {

    private final AwsSessionCredentials credentials;

    public S3Provider() {
        this.credentials = AwsSessionCredentials.create(
                AppConfig.get("bucket.access.key"),
                AppConfig.get("bucket.secret.access.key"),
                AppConfig.get("bucket.session.token")
        );
    }

    public S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(() -> credentials)
                .build();
    }

}
