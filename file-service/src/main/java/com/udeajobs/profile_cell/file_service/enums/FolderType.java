package com.udeajobs.profile_cell.file_service.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * Enumeración que define las carpetas permitidas dentro del bucket de almacenamiento.
 * Esto previene ataques de inyección de rutas y asegura que solo se acceda a carpetas autorizadas.
 */
@Getter
@Schema(
        description = "Tipos de carpetas disponibles en el sistema de almacenamiento",
        example = "PROFILE_IMAGES"
)
public enum FolderType {
    /**
     * Carpeta para almacenar imágenes de perfil de usuario
     */
    @Schema(description = "Carpeta para imágenes de perfil de usuario (fotos de perfil, avatares)")
    PROFILE_IMAGES("profile_images"),

    /**
     * Carpeta para almacenar CVs y hojas de vida de usuarios
     */
    @Schema(description = "Carpeta para CVs y hojas de vida de usuarios (documentos PDF, DOCX)")
    PROFILE_CVS("profile_cvs");


    /**
     * -- GETTER --
     *  Obtiene el nombre de la carpeta.
     */
    private final String folderName;

    /**
     * Constructor del enum FolderType.
     *
     * @param folderName nombre de la carpeta en el almacenamiento S3/MinIO
     */
    FolderType(String folderName) {
        this.folderName = folderName;
    }

}

