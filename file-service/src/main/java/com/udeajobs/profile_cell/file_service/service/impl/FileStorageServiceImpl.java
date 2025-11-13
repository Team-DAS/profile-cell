package com.udeajobs.profile_cell.file_service.service.impl;

import com.udeajobs.profile_cell.file_service.dto.FileUploadResponse;
import com.udeajobs.profile_cell.file_service.enums.FolderType;
import com.udeajobs.profile_cell.file_service.exception.FileDeleteException;
import com.udeajobs.profile_cell.file_service.exception.FileNotFoundException;
import com.udeajobs.profile_cell.file_service.exception.FileUploadException;
import com.udeajobs.profile_cell.file_service.service.IFileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.UUID;

/**
 * Implementación del servicio de almacenamiento de archivos.
 * Maneja las operaciones de subida, descarga y eliminación de archivos en S3/MinIO
 * usando un único bucket con carpetas organizadas por tipo.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements IFileStorageService {

    private final S3Client s3Client;

    @Value("${s3.endpoint-url}")
    private String endpointUrl;

    @Value("${s3.bucket-name}")
    private String bucketName;

    /**
     * Sube un archivo a la carpeta especificada dentro del bucket S3/MinIO.
     * Genera un nombre único combinando un UUID con el nombre original del archivo.
     *
     * @param folderType tipo de carpeta donde se almacenará el archivo
     * @param objectName nombre del objeto en el almacenamiento
     * @param file archivo multipart a subir
     * @return FileUploadResponse con información del archivo subido
     * @throws FileUploadException si ocurre un error durante la subida
     */
    @Override
    public FileUploadResponse uploadFile(FolderType folderType, String objectName, MultipartFile file) {
        validateFile(file);

        String folderName = folderType.getFolderName();
        String uniqueObjectName = generateUniqueObjectName(objectName);
        String fullObjectKey = folderName + "/" + uniqueObjectName;

        try {
            log.info("Subiendo archivo {} al bucket {} en carpeta {}", uniqueObjectName, bucketName, folderName);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullObjectKey)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            log.info("Archivo {} subido exitosamente al bucket {} en carpeta {}", uniqueObjectName, bucketName, folderName);

            String fileUrl = String.format("%s/%s/%s", endpointUrl, bucketName, fullObjectKey);

            return FileUploadResponse.builder()
                    .objectName(uniqueObjectName)
                    .fileUrl(fileUrl)
                    .contentType(file.getContentType())
                    .size(file.getSize())
                    .folderType(folderType.name())
                    .build();

        } catch (S3Exception e) {
            log.error("Error de S3 al subir archivo: {}", e.getMessage(), e);
            throw new FileUploadException("Error al subir el archivo al almacenamiento S3: " + e.getMessage(), e);
        } catch (IOException e) {
            log.error("Error de IO al leer archivo: {}", e.getMessage(), e);
            throw new FileUploadException("Error al leer el archivo: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error inesperado al subir archivo: {}", e.getMessage(), e);
            throw new FileUploadException("Error inesperado al subir el archivo", e);
        }
    }

    /**
     * Descarga un archivo de la carpeta especificada dentro del bucket.
     * Configura los headers apropiados para forzar la descarga en el navegador.
     *
     * @param folderType tipo de carpeta desde donde se descargará el archivo
     * @param objectName nombre del objeto en el almacenamiento
     * @return ResponseEntity con el recurso del archivo y headers configurados
     * @throws FileNotFoundException si el archivo no existe
     */
    @Override
    public ResponseEntity<Resource> downloadFile(FolderType folderType, String objectName) {
        String folderName = folderType.getFolderName();
        String fullObjectKey = folderName + "/" + objectName;

        try {
            log.info("Descargando archivo {} del bucket {} en carpeta {}", objectName, bucketName, folderName);

            // Primero verificamos si el objeto existe
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullObjectKey)
                    .build();

            HeadObjectResponse headObjectResponse = s3Client.headObject(headObjectRequest);

            // Obtenemos el objeto
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullObjectKey)
                    .build();

            InputStreamResource resource = new InputStreamResource(s3Client.getObject(getObjectRequest));

            // Configuramos los headers para la descarga
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(
                    headObjectResponse.contentType() != null ? headObjectResponse.contentType() : "application/octet-stream"
            ));
            headers.setContentLength(headObjectResponse.contentLength());
            headers.setContentDispositionFormData("attachment", objectName);

            log.info("Archivo {} descargado exitosamente del bucket {} en carpeta {}", objectName, bucketName, folderName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(resource);

        } catch (NoSuchKeyException e) {
            log.error("Archivo no encontrado: {} en bucket {} carpeta {}", objectName, bucketName, folderName);
            throw new FileNotFoundException(
                    String.format("El archivo '%s' no existe en la carpeta '%s'", objectName, folderName), e
            );
        } catch (S3Exception e) {
            log.error("Error de S3 al descargar archivo: {}", e.getMessage(), e);
            throw new FileNotFoundException("Error al acceder al archivo en el almacenamiento S3: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error inesperado al descargar archivo: {}", e.getMessage(), e);
            throw new FileNotFoundException("Error inesperado al descargar el archivo", e);
        }
    }

    /**
     * Elimina un archivo de la carpeta especificada dentro del bucket.
     *
     * @param folderType tipo de carpeta desde donde se eliminará el archivo
     * @param objectName nombre del objeto en el almacenamiento
     * @throws FileDeleteException si ocurre un error durante la eliminación
     */
    @Override
    public void deleteFile(FolderType folderType, String objectName) {
        String folderName = folderType.getFolderName();
        String fullObjectKey = folderName + "/" + objectName;

        try {
            log.info("Eliminando archivo {} del bucket {} en carpeta {}", objectName, bucketName, folderName);

            // Verificamos si el objeto existe antes de eliminarlo
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullObjectKey)
                    .build();

            s3Client.headObject(headObjectRequest);

            // Eliminamos el objeto
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fullObjectKey)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

            log.info("Archivo {} eliminado exitosamente del bucket {} en carpeta {}", objectName, bucketName, folderName);

        } catch (NoSuchKeyException e) {
            log.error("Archivo no encontrado para eliminar: {} en bucket {} carpeta {}", objectName, bucketName, folderName);
            throw new FileNotFoundException(
                    String.format("El archivo '%s' no existe en la carpeta '%s'", objectName, folderName), e
            );
        } catch (S3Exception e) {
            log.error("Error de S3 al eliminar archivo: {}", e.getMessage(), e);
            throw new FileDeleteException("Error al eliminar el archivo del almacenamiento S3: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("Error inesperado al eliminar archivo: {}", e.getMessage(), e);
            throw new FileDeleteException("Error inesperado al eliminar el archivo", e);
        }
    }

    /**
     * Valida que el archivo no sea nulo y no esté vacío.
     *
     * @param file archivo a validar
     * @throws IllegalArgumentException si el archivo es inválido
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo no puede ser nulo o vacío");
        }

        if (file.getOriginalFilename() == null || file.getOriginalFilename().isBlank()) {
            throw new IllegalArgumentException("El nombre del archivo no puede ser nulo o vacío");
        }
    }

    /**
     * Genera un nombre único para el objeto combinando un UUID con el nombre original.
     * Esto previene conflictos de nombres y asegura unicidad.
     *
     * @param originalName nombre original del archivo
     * @return nombre único generado
     */
    private String generateUniqueObjectName(String originalName) {
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + originalName;
    }
}

