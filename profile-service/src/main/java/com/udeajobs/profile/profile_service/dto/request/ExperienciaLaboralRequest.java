package com.udeajobs.profile.profile_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Experiencia laboral del usuario")
public class ExperienciaLaboralRequest {

    /**
     * Nombre de la empresa
     */
    @Schema(
            description = "Nombre de la empresa u organización",
            example = "Tech Solutions Inc.",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 2,
            maxLength = 200
    )
    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(min = 2, max = 200, message = "El nombre de la empresa debe tener entre 2 y 200 caracteres")
    private String empresa;

    /**
     * Puesto o cargo desempeñado
     */
    @Schema(
            description = "Puesto o cargo desempeñado en la empresa",
            example = "Backend Developer",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 2,
            maxLength = 200
    )
    @NotBlank(message = "El puesto es obligatorio")
    @Size(min = 2, max = 200, message = "El puesto debe tener entre 2 y 200 caracteres")
    private String puesto;

    /**
     * Fecha de inicio del empleo
     */
    @Schema(
            description = "Fecha de inicio del empleo",
            example = "2018-01-01",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "string",
            format = "date"
    )
    @NotNull(message = "La fecha de inicio es obligatoria")
    @PastOrPresent(message = "La fecha de inicio no puede ser futura")
    private LocalDate fechaInicio;

    /**
     * Fecha de finalización del empleo (null si es trabajo actual)
     */
    @Schema(
            description = "Fecha de finalización del empleo (null si es el trabajo actual)",
            example = "2023-09-30",
            type = "string",
            format = "date"
    )
    @PastOrPresent(message = "La fecha de fin no puede ser futura")
    private LocalDate fechaFin;

    /**
     * Descripción de responsabilidades y logros
     */
    @Schema(
            description = "Descripción de responsabilidades, logros y tecnologías utilizadas",
            example = "Desarrollo de microservicios para la plataforma X utilizando Spring Boot, Kubernetes y AWS.",
            maxLength = 2000
    )
    @Size(max = 2000, message = "La descripción no puede exceder los 2000 caracteres")
    private String descripcion;
}
