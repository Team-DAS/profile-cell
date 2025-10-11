package com.udeajobs.profile_cell.file_service.controller;

import com.udeajobs.profile_cell.file_service.dto.FileUploadResponse;
import com.udeajobs.profile_cell.file_service.enums.BucketType;
import com.udeajobs.profile_cell.file_service.service.IFileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador REST para la gestión de archivos en almacenamiento S3/MinIO.
 * Expone endpoints para subir, descargar y eliminar archivos.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final IFileStorageService fileStorageService;

    /**
     * Endpoint para subir un archivo al bucket especificado.
     *
     * @param bucketType tipo de bucket (PROFILES, PROJECTS)
     * @param file archivo multipart a subir
     * @return FileUploadResponse con información del archivo subido
     */
    @PostMapping("/{bucketType}")
    public ResponseEntity<FileUploadResponse> uploadFile(
            @PathVariable BucketType bucketType,
            @RequestParam("file") MultipartFile file) {

        log.info("Petición de subida de archivo al bucket: {}", bucketType);

        String originalFilename = file.getOriginalFilename();
        FileUploadResponse response = fileStorageService.uploadFile(bucketType, originalFilename, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint para descargar un archivo del bucket especificado.
     * La expresión regular .+ en objectName permite manejar nombres con puntos.
     *
     * @param bucketType tipo de bucket (PROFILES, PROJECTS)
     * @param objectName nombre del objeto en el almacenamiento
     * @return archivo como recurso descargable
     */
    @GetMapping("/{bucketType}/{objectName:.+}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable BucketType bucketType,
            @PathVariable String objectName) {

        log.info("Petición de descarga de archivo: {} del bucket: {}", objectName, bucketType);

        return fileStorageService.downloadFile(bucketType, objectName);
    }

    /**
     * Endpoint para eliminar un archivo del bucket especificado.
     * La expresión regular .+ en objectName permite manejar nombres con puntos.
     *
     * @param bucketType tipo de bucket (PROFILES, PROJECTS)
     * @param objectName nombre del objeto en el almacenamiento
     * @return ResponseEntity sin contenido (204 No Content)
     */
    @DeleteMapping("/{bucketType}/{objectName:.+}")
    public ResponseEntity<Void> deleteFile(
            @PathVariable BucketType bucketType,
            @PathVariable String objectName) {

        log.info("Petición de eliminación de archivo: {} del bucket: {}", objectName, bucketType);

        fileStorageService.deleteFile(bucketType, objectName);

        return ResponseEntity.noContent().build();
    }
}

