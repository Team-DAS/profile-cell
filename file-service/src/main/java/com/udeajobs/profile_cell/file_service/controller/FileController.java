package com.udeajobs.profile_cell.file_service.controller;

import com.udeajobs.profile_cell.file_service.dto.FileUploadResponse;
import com.udeajobs.profile_cell.file_service.enums.FolderType;
import com.udeajobs.profile_cell.file_service.service.IFileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
@Tag(name = "File Management", description = "API para la gestión de archivos en almacenamiento S3/MinIO. Permite subir, descargar y eliminar archivos organizados en carpetas dentro de un bucket único.")
public class FileController {

    private final IFileStorageService fileStorageService;

    /**
     * Endpoint para subir un archivo a la carpeta especificada.
     *
     * @param folderType tipo de carpeta (PROFILE_IMAGES, PROFILE_CVS)
     * @param file archivo multipart a subir
     * @return FileUploadResponse con información del archivo subido
     */
    @Operation(
            summary = "Subir un archivo",
            description = """
                    Sube un archivo a la carpeta especificada en el almacenamiento S3/MinIO.
                    
                    El archivo se almacena con un nombre único generado (UUID + nombre original)
                    para evitar colisiones. El sistema valida el tipo de archivo y retorna
                    información detallada del archivo almacenado, incluyendo la URL de acceso.
                    
                    **Ejemplo de uso:**
                    - Fotos de perfil → carpeta `PROFILE_IMAGES`
                    - CVs de usuarios → carpeta `PROFILE_CVS`
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Archivo subido exitosamente"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Archivo inválido o carpeta no válida"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor al procesar el archivo"
            )
    })
    @PostMapping(value = "/{folderType}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FileUploadResponse> uploadFile(
            @Parameter(
                    description = "Tipo de carpeta donde se almacenará el archivo",
                    required = true,
                    example = "PROFILE_IMAGES",
                    schema = @Schema(implementation = FolderType.class)
            )
            @PathVariable FolderType folderType,

            @Parameter(
                    description = "Archivo a subir (FormData)",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestParam("file") MultipartFile file) {

        log.info("Petición de subida de archivo a la carpeta: {}", folderType);

        String originalFilename = file.getOriginalFilename();
        FileUploadResponse response = fileStorageService.uploadFile(folderType, originalFilename, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint para descargar un archivo de la carpeta especificada.
     * La expresión regular .+ en objectName permite manejar nombres con puntos.
     *
     * @param folderType tipo de carpeta (PROFILE_IMAGES, PROFILE_CVS)
     * @param objectName nombre del objeto en el almacenamiento
     * @return archivo como recurso descargable
     */
    @Operation(
            summary = "Descargar un archivo",
            description = """
                    Descarga un archivo de la carpeta especificada en el almacenamiento S3/MinIO.
                    
                    Retorna el archivo como un recurso binario con los headers apropiados
                    (Content-Type, Content-Disposition) para que el navegador pueda manejarlo
                    correctamente, ya sea mostrándolo inline o descargándolo.
                    
                    **Nota:** El objectName debe incluir la extensión del archivo
                    (ej: `550e8400-e29b-41d4-a716-446655440000_profile.jpg`)
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Archivo descargado exitosamente"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Archivo no encontrado en la carpeta especificada"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor al recuperar el archivo"
            )
    })
    @GetMapping("/{folderType}/{objectName:.+}")
    public ResponseEntity<Resource> downloadFile(
            @Parameter(
                    description = "Tipo de carpeta donde se encuentra el archivo",
                    required = true,
                    example = "PROFILE_IMAGES",
                    schema = @Schema(implementation = FolderType.class)
            )
            @PathVariable FolderType folderType,

            @Parameter(
                    description = "Nombre del archivo en el almacenamiento (incluye UUID y nombre original)",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000_profile.jpg"
            )
            @PathVariable String objectName) {

        log.info("Petición de descarga de archivo: {} de la carpeta: {}", objectName, folderType);

        return fileStorageService.downloadFile(folderType, objectName);
    }

    /**
     * Endpoint para eliminar un archivo de la carpeta especificada.
     * La expresión regular .+ en objectName permite manejar nombres con puntos.
     *
     * @param folderType tipo de carpeta (PROFILE_IMAGES, PROFILE_CVS)
     * @param objectName nombre del objeto en el almacenamiento
     * @return ResponseEntity sin contenido (204 No Content)
     */
    @Operation(
            summary = "Eliminar un archivo",
            description = """
                    Elimina un archivo de la carpeta especificada en el almacenamiento S3/MinIO.
                    
                    Esta operación es permanente y no se puede deshacer. El archivo
                    se elimina completamente del almacenamiento.
                    
                    **Nota:** Asegúrate de que el objectName sea correcto antes de eliminar.
                    Esta acción no puede revertirse.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Archivo eliminado exitosamente (sin contenido)"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Archivo no encontrado en la carpeta especificada"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Error interno del servidor al eliminar el archivo"
            )
    })
    @DeleteMapping("/{folderType}/{objectName:.+}")
    public ResponseEntity<Void> deleteFile(
            @Parameter(
                    description = "Tipo de carpeta donde se encuentra el archivo a eliminar",
                    required = true,
                    example = "PROFILE_IMAGES",
                    schema = @Schema(implementation = FolderType.class)
            )
            @PathVariable FolderType folderType,

            @Parameter(
                    description = "Nombre del archivo en el almacenamiento (incluye UUID y extensión)",
                    required = true,
                    example = "550e8400-e29b-41d4-a716-446655440000_profile.jpg"
            )
            @PathVariable String objectName) {

        log.info("Petición de eliminación de archivo: {} de la carpeta: {}", objectName, folderType);

        fileStorageService.deleteFile(folderType, objectName);

        return ResponseEntity.noContent().build();
    }
}


