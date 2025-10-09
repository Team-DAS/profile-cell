package com.udeajobs.profile.profile_service.entity;

import com.udeajobs.profile.profile_service.enums.NivelHabilidad;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad embebida que representa una habilidad técnica o blanda del usuario.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Habilidad {

    /**
     * Identificador único de la habilidad dentro del perfil
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
