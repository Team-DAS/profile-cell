package com.udeajobs.profile_cell.file_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;

/**
 * Clase de configuración para el cliente S3 (compatible con MinIO).
 * Centraliza la creación y configuración del cliente S3 usado en toda la aplicación.
 */
@Configuration
@Slf4j
public class S3Config {

    @Value("${s3.endpoint-url}")
    private String endpointUrl;

    @Value("${s3.access-key}")
    private String accessKey;

    @Value("${s3.secret-key}")
    private String secretKey;

    @Value("${s3.region}")
    private String region;

    /**
     * Crea y configura el bean S3Client para interactuar con el almacenamiento S3/MinIO.
     * Habilita path-style-access para compatibilidad con MinIO.
     *
     * @return instancia configurada de S3Client
     */
    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        S3Configuration s3Config = S3Configuration.builder()
                .pathStyleAccessEnabled(true)  // Crucial para MinIO
                .build();

        log.info("Valor real de endpointUrl = '{}'", endpointUrl);

        System.out.println("✅ Endpoint URL recibido: [" + endpointUrl + "]");

        return S3Client.builder()
                .endpointOverride(URI.create(endpointUrl))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.of(region))
                .serviceConfiguration(s3Config)
                .build();
    }
}

