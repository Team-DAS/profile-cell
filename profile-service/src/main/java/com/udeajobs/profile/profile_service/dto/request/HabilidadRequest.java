package com.udeajobs.profile.profile_service.dto.request;

import com.udeajobs.profile.profile_service.enums.NivelHabilidad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de request para crear o actualizar una habilidad.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HabilidadRequest {

    /**
     * Nombre de la habilidad
     */
    @NotBlank(message = "El nombre de la habilidad es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre de la habilidad debe tener entre 2 y 100 caracteres")
    private String nombre;

    /**
     * Nivel de dominio de la habilidad
     */
    @NotNull(message = "El nivel de habilidad es obligatorio")
    private NivelHabilidad nivel;
}
