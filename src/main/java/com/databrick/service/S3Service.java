package com.databrick.service;

import com.databrick.config.S3Provider;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class S3Service {

    private final S3Client s3Client = new S3Provider().getS3Client();
    private final LoggingUtility log = new LoggingUtility();
    private String bucketName;

    public S3Service(String bucketName) {
        this.bucketName = bucketName;
    }

    public void createBucket() {
        try {
            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();
            s3Client.createBucket(createBucketRequest);
            log.registerLog(Level.INFO, "Bucket criado com sucesso");
        } catch (S3Exception e) {
            System.err.println("Erro ao criar o bucket: " + e.getMessage());
        }
    }

    public List<Bucket> listBucket() {
        try {
            List<Bucket> buckets = s3Client.listBuckets().buckets();
            log.registerLog(Level.INFO, "Lista de buckets coletadas");
            return buckets;
        } catch (S3Exception e) {
            log.registerLog(Level.ERROR, "Falha ao tentar coletar os buckets. Message: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public List<InputStream> bucketObjectList(String fileName) {
        try {
            ListObjectsRequest listObjects = ListObjectsRequest.builder()
                    .bucket(bucketName)
                    .prefix(fileName)
                    .build();

            List<S3Object> objectList = s3Client.listObjects(listObjects).contents();
            List<InputStream> inputStreamList = new ArrayList<>();
            for (S3Object s3Object : objectList) {
                String key = s3Object.key();

                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();

                inputStreamList.add(s3Client.getObject(getObjectRequest));
            }
            return inputStreamList;
        } catch (S3Exception e) {
            log.registerLog(Level.ERROR, "Falha ao buscar os objetos do bucket. Message: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public void uploadFile(String fileName) {
        try {
            String uniqueFileName = UUID.randomUUID().toString();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(uniqueFileName)
                    .build();

            File file = new File(fileName);
            s3Client.putObject(putObjectRequest, RequestBody.fromFile(file));

            log.registerLog(Level.INFO, "Upload do arquivo concluído com sucesso");
        } catch (S3Exception e) {
            log.registerLog(Level.ERROR, "Falha ao enviar o arquivo. Message: " + e.getMessage());
        }
    }

    public void downloadFile() {
        try {
            List<S3Object> objects = s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build()).contents();
            for (S3Object object : objects) {
                GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(object.key())
                        .build();

                InputStream inputStream = s3Client.getObject(getObjectRequest, ResponseTransformer.toInputStream());
                Files.copy(inputStream, new File(object.key()).toPath());
                System.out.println("Arquivo baixado: " + object.key());
            }
        } catch (IOException | S3Exception e) {
            System.err.println("Erro ao fazer download dos arquivos: " + e.getMessage());
        }
    }

    public void deleteObject(String objectKeyToDelete) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKeyToDelete)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);

            log.registerLog(Level.INFO, "Objeto " + objectKeyToDelete + " deletado com sucesso");
        } catch (S3Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar deletar o objeto " + objectKeyToDelete + ". Message: " + e.getMessage());
        }
    }

}
