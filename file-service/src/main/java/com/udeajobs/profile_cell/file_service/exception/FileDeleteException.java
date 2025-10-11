package com.udeajobs.profile_cell.file_service.exception;

/**
 * Excepción lanzada cuando ocurre un error durante la eliminación de archivos.
 */
public class FileDeleteException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message mensaje descriptivo del error
     */
    public FileDeleteException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param message mensaje descriptivo del error
     * @param cause causa raíz de la excepción
     */
    public FileDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
