package com.udeajobs.profile.profile_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO de respuesta para información educativa.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducacionResponse {

    /**
     * Identificador único de la educación
     */
    private String id;

    /**
     * Nombre de la institución educativa
     */
    private String institucion;

    /**
     * Título o grado obtenido
     */
    private String titulo;

    /**
     * Fecha de finalización de los estudios
     */
    private LocalDate fechaFin;
}
