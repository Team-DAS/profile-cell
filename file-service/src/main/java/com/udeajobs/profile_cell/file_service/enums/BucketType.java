package com.udeajobs.profile_cell.file_service.enums;

import lombok.Getter;

/**
 * Enumeración que define los tipos de buckets permitidos en el sistema.
 * Esto previene ataques de inyección de rutas y asegura que solo se acceda a buckets autorizados.
 */
@Getter
public enum BucketType {
    /**
     * Bucket para almacenar archivos relacionados con perfiles de usuario
     */
    PROFILES("profiles");


    /**
     * -- GETTER --
     *  Obtiene el nombre del bucket.
     */
    private final String bucketName;

    /**
     * Constructor del enum BucketType.
     *
     * @param bucketName nombre del bucket en el almacenamiento S3/MinIO
     */
    BucketType(String bucketName) {
        this.bucketName = bucketName;
    }

}

