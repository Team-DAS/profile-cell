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
 * DTO de request para crear o actualizar una experiencia laboral.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExperienciaLaboralRequest {

    /**
     * Nombre de la empresa
     */
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(min = 2, max = 200, message = "El nombre de la empresa debe tener entre 2 y 200 caracteres")
    private String empresa;

    /**
     * Puesto o cargo desempeñado
     */
    @NotBlank(message = "El puesto es obligatorio")
    @Size(min = 2, max = 200, message = "El puesto debe tener entre 2 y 200 caracteres")
    private String puesto;

    /**
     * Fecha de inicio del empleo
     */
    @NotNull(message = "La fecha de inicio es obligatoria")
    @PastOrPresent(message = "La fecha de inicio no puede ser futura")
    private LocalDate fechaInicio;

    /**
     * Fecha de finalización del empleo (null si es trabajo actual)
     */
    @PastOrPresent(message = "La fecha de fin no puede ser futura")
    private LocalDate fechaFin;

    /**
     * Descripción de responsabilidades y logros
     */
    @Size(max = 2000, message = "La descripción no puede exceder los 2000 caracteres")
    private String descripcion;
}
