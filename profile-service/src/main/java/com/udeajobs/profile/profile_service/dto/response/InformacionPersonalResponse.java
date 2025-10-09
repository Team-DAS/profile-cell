package com.udeajobs.profile.profile_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para la información personal del perfil.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacionPersonalResponse {

    /**
     * Nombre completo del usuario
     */
    private String nombreCompleto;

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
    private UbicacionResponse ubicacion;

    /**
     * URL de la foto de perfil
     */
    private String fotoUrl;
}
