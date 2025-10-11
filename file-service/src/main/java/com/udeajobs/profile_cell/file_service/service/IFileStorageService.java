package com.udeajobs.profile_cell.file_service.service;

import com.udeajobs.profile_cell.file_service.dto.FileUploadResponse;
import com.udeajobs.profile_cell.file_service.enums.BucketType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interfaz que define el contrato para las operaciones de almacenamiento de archivos.
 * Proporciona métodos para subir, descargar y eliminar archivos en almacenamiento S3/MinIO.
 */
public interface IFileStorageService {

    /**
     * Sube un archivo al bucket especificado.
     *
     * @param bucketType tipo de bucket donde se almacenará el archivo
     * @param objectName nombre del objeto en el almacenamiento (debe ser único)
     * @param file archivo multipart a subir
     * @return FileUploadResponse con información del archivo subido
     * @throws com.udeajobs.profile_cell.file_service.exception.FileUploadException si ocurre un error durante la subida
     */
    FileUploadResponse uploadFile(BucketType bucketType, String objectName, MultipartFile file);

    /**
     * Descarga un archivo del bucket especificado.
     *
     * @param bucketType tipo de bucket desde donde se descargará el archivo
     * @param objectName nombre del objeto en el almacenamiento
     * @return ResponseEntity con el recurso del archivo y headers apropiados
     * @throws com.udeajobs.profile_cell.file_service.exception.FileNotFoundException si el archivo no existe
     */
    ResponseEntity<Resource> downloadFile(BucketType bucketType, String objectName);

    /**
     * Elimina un archivo del bucket especificado.
     *
     * @param bucketType tipo de bucket desde donde se eliminará el archivo
     * @param objectName nombre del objeto en el almacenamiento
     * @throws com.udeajobs.profile_cell.file_service.exception.FileDeleteException si ocurre un error durante la eliminación
     */
    void deleteFile(BucketType bucketType, String objectName);
}

