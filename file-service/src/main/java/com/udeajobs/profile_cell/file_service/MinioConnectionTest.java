package com.udeajobs.profile_cell.file_service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

@Component
@Slf4j
public class MinioConnectionTest implements CommandLineRunner {

    private final S3Client s3Client;

    @Value("${s3.bucket-name}")
    private String bucketName;

    public MinioConnectionTest(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void run(String... args) {
        try {
            log.info("🔍 Probando conexión a GCS...");
            log.info("📦 Bucket objetivo: {}", bucketName);

            // ✅ Verificar que el bucket existe (no requiere storage.buckets.list)
            HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

            s3Client.headBucket(headBucketRequest);
            log.info("✅ Bucket '{}' accesible", bucketName);

            // ✅ Listar objetos en el bucket (solo requiere storage.objects.list)
            ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                    .bucket(bucketName)
                    .maxKeys(10)
                    .build();

            ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);

            if (listResponse.contents().isEmpty()) {
                log.info("📂 El bucket está vacío (0 objetos)");
            } else {
                log.info("📁 Objetos en el bucket (mostrando primeros 10):");
                listResponse.contents().forEach(s3Object ->
                        log.info("  - {} ({} bytes)", s3Object.key(), s3Object.size())
                );
            }

            log.info("✅ Conexión a GCS exitosa!");

        } catch (software.amazon.awssdk.services.s3.model.S3Exception e) {
            log.error("❌ Error S3: Status {}, Code: {}, Message: {}",
                    e.statusCode(), e.awsErrorDetails().errorCode(), e.awsErrorDetails().errorMessage());

            if (e.statusCode() == 403) {
                log.error("💡 Verifica que el Service Account tenga permisos 'Storage Object Admin' sobre el bucket '{}'", bucketName);
            } else if (e.statusCode() == 404) {
                log.error("💡 El bucket '{}' no existe o no tienes acceso a él", bucketName);
            }
        } catch (Exception e) {
            log.error("❌ Error inesperado al conectar:", e);
        }
    }
}