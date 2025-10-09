package com.udeajobs.profile.profile_service.exception;

/**
 * Excepción lanzada cuando no se encuentra un perfil de usuario.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
public class ProfileNotFoundException extends RuntimeException {

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message mensaje descriptivo del error
     */
    public ProfileNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con userId.
     *
     * @param userId identificador del usuario cuyo perfil no fue encontrado
     * @return instancia de la excepción
     */
    public static ProfileNotFoundException forUserId(String userId) {
        return new ProfileNotFoundException("Perfil no encontrado para el usuario con ID: " + userId);
    }
}
