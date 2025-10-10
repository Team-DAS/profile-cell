package com.udeajobs.profile.profile_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad embebida que representa la información personal básica del perfil de un usuario.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacionPersonal {

    /**
     * Nombre completo del usuario
     */
    private String nombreCompleto;

    /**
     * Correo electrónico del usuario
     */
    private String email;

    /**
     * Título profesional del usuario
     */
    private String tituloProfesional;

    /**
     * Resumen o descripción del perfil profesional
     */
    private String resumen;

    /**
     * Ubicación geográfica del usuario
     */
    private Ubicacion ubicacion;

    /**
     * URL de la foto de perfil almacenada en el file-service
     */
    private String fotoUrl;
}
