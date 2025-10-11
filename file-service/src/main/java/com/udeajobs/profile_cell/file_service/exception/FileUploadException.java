package com.udeajobs.profile_cell.file_service.exception;

/**
 * Excepción lanzada cuando ocurre un error durante la subida de archivos.
 */
public class FileUploadException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message mensaje descriptivo del error
     */
    public FileUploadException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param message mensaje descriptivo del error
     * @param cause causa raíz de la excepción
     */
    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}

