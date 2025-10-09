package com.udeajobs.profile.profile_service.exception;

/**
 * Excepción lanzada cuando los datos proporcionados no son válidos.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
public class InvalidDataException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message mensaje descriptivo del error
     */
    public InvalidDataException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param message mensaje descriptivo del error
     * @param cause causa original del error
     */
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

