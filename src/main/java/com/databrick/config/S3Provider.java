package com.databrick.config;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.Properties;

public class S3Provider {

    private final AwsSessionCredentials credentials;
    private Properties properties = new Properties();

    public S3Provider() {
        this.credentials = AwsSessionCredentials.create(
                properties.getProperty("bucket.access.key"),
                properties.getProperty("bucket.secret.access.key"),
                properties.getProperty("bucket.session.token")
        );
    }

    public S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(() -> credentials)
                .build();
    }

}
