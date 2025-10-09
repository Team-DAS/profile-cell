package com.udeajobs.profile.profile_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO de respuesta para una experiencia laboral.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienciaLaboralResponse {

    /**
     * Identificador único de la experiencia
     */
    private String id;

    /**
     * Nombre de la empresa
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
     * Fecha de finalización del empleo
     */
    private LocalDate fechaFin;

    /**
     * Descripción de responsabilidades y logros
     */
    private String descripcion;
}
