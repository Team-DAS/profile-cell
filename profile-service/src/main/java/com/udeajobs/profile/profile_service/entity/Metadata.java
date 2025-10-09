package com.udeajobs.profile.profile_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad embebida que contiene metadatos del perfil del usuario.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

    /**
     * Indica si el perfil está completo con toda la información requerida
     */
    private Boolean perfilCompleto;

    /**
     * Fecha y hora de creación del perfil
     */
    private LocalDateTime fechaCreacion;

    /**
     * Fecha y hora de la última actualización del perfil
     */
    private LocalDateTime ultimaActualizacion;
}
