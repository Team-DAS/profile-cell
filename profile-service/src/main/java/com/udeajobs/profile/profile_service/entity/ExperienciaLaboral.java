package com.udeajobs.profile.profile_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidad embebida que representa una experiencia laboral del usuario.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienciaLaboral {

    /**
     * Identificador único de la experiencia dentro del perfil
     */
    private String id;

    /**
     * Nombre de la empresa donde trabajó
     */
    private String empresa;

    /**
     * Puesto o cargo desempeñado
     */
    private String puesto;

    /**
     * Fecha de inicio del empleo
     */
    private LocalDate fechaInicio;

    /**
     * Fecha de finalización del empleo (null si es el trabajo actual)
     */
    private LocalDate fechaFin;

    /**
     * Descripción de las responsabilidades y logros
     */
    private String descripcion;
}
