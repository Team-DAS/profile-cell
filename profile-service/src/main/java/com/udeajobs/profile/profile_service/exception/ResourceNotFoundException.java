package com.udeajobs.profile.profile_service.exception;

/**
 * Excepción lanzada cuando no se encuentra un recurso específico dentro de un perfil
 * (experiencia, educación, habilidad, portafolio).
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message mensaje descriptivo del error
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Crea una excepción para un recurso no encontrado.
     *
     * @param resourceType tipo de recurso (experiencia, educación, etc.)
     * @param resourceId identificador del recurso
     * @param userId identificador del usuario
     * @return instancia de la excepción
     */
    public static ResourceNotFoundException forResource(String resourceType, String resourceId, String userId) {
        return new ResourceNotFoundException(
            String.format("%s con ID '%s' no encontrado en el perfil del usuario '%s'",
                resourceType, resourceId, userId)
        );
    }
}

