package com.udeajobs.profile_cell.file_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para la respuesta de subida de archivos.
 * Contiene información relevante sobre el archivo almacenado.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {

    /**
     * Nombre del objeto en el almacenamiento (incluye UUID)
     */
    private String objectName;

    /**
     * URL completa para acceder al archivo
     */
    private String fileUrl;

    /**
     * Tipo de contenido del archivo (MIME type)
     */
    private String contentType;

    /**
     * Tamaño del archivo en bytes
     */
    private Long size;

    /**
     * Tipo de bucket donde se almacenó el archivo
     */
    private String bucketType;

    /**
     * Timestamp de cuando se subió el archivo
     */
    @Builder.Default
    private LocalDateTime uploadedAt = LocalDateTime.now();
}

