package com.udeajobs.profile_cell.file_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

@Component
@Slf4j
public class MinioConnectionTest implements CommandLineRunner {

    private final S3Client s3Client;

    public MinioConnectionTest(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void run(String... args) {
        try {
            ListBucketsResponse buckets = s3Client.listBuckets();
            System.out.println("✅ MinIO conectado. Buckets disponibles:");
            buckets.buckets().forEach(b -> System.out.println("- " + b.name()));
        } catch (Exception e) {
            System.err.println("❌ Error al conectar con MinIO:");
            e.printStackTrace();
        }
    }
}
