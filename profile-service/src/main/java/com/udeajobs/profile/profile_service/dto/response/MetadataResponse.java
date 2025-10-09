package com.udeajobs.profile.profile_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de respuesta para los metadatos del perfil.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetadataResponse {

    /**
     * Indica si el perfil está completo
     */
    private Boolean perfilCompleto;

    /**
     * Fecha y hora de creación del perfil
     */
    private LocalDateTime fechaCreacion;

    /**
     * Fecha y hora de la última actualización
     */
    private LocalDateTime ultimaActualizacion;
}
