package com.udeajobs.profile_cell.file_service.exception;

/**
 * Excepción lanzada cuando un archivo solicitado no existe en el almacenamiento.
 */
public class FileNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message mensaje descriptivo del error
     */
    public FileNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param message mensaje descriptivo del error
     * @param cause causa raíz de la excepción
     */
    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

