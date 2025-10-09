package com.udeajobs.profile.profile_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Entidad embebida que representa la formación académica del usuario.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Educacion {

    /**
     * Identificador único de la educación dentro del perfil
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
