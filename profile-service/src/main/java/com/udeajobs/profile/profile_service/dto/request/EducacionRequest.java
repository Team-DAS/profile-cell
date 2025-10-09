package com.udeajobs.profile.profile_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO de request para crear o actualizar información educativa.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducacionRequest {

    /**
     * Nombre de la institución educativa
     */
    @NotBlank(message = "La institución es obligatoria")
    @Size(min = 3, max = 200, message = "El nombre de la institución debe tener entre 3 y 200 caracteres")
    private String institucion;

    /**
     * Título o grado obtenido
     */
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
    private String titulo;

    /**
     * Fecha de finalización de los estudios
     */
    @NotNull(message = "La fecha de finalización es obligatoria")
    @PastOrPresent(message = "La fecha de finalización no puede ser futura")
    private LocalDate fechaFin;
}
