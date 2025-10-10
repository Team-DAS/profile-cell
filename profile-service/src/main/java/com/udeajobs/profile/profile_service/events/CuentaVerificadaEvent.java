package com.udeajobs.profile.profile_service.events;

/**
 * Evento que se recibe cuando una cuenta de usuario ha sido verificada exitosamente.
 *
 * @param accountId identificador único de la cuenta verificada
 * @param fullName nombre completo del usuario verificado
 * @param email dirección de correo electrónico del usuario verificado
 *
 * @author UdeAJobs Team
 * @version 1.0
 * @since 1.0
 */
public record CuentaVerificadaEvent(
        String accountId,
        String fullName,
        String email
) {
}
