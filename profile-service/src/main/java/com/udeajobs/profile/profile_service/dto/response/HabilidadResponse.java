package com.udeajobs.profile.profile_service.dto.response;

import com.udeajobs.profile.profile_service.enums.NivelHabilidad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para una habilidad.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabilidadResponse {

    /**
     * Identificador único de la habilidad
     */
    private String id;

    /**
     * Nombre de la habilidad
     */
    private String nombre;

    /**
     * Nivel de dominio de la habilidad
     */
    private NivelHabilidad nivel;
}
