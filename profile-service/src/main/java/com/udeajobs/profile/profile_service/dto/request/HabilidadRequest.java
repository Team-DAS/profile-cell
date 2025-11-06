package com.udeajobs.profile.profile_service.dto.request;

import com.udeajobs.profile.profile_service.enums.NivelHabilidad;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Habilidad o competencia técnica del usuario")
public class HabilidadRequest {

    /**
     * Nombre de la habilidad
     */
    @Schema(
            description = "Nombre de la habilidad o tecnología",
            example = "Python",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 2,
            maxLength = 100
    )
    @NotBlank(message = "El nombre de la habilidad es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre de la habilidad debe tener entre 2 y 100 caracteres")
    private String nombre;

    /**
     * Nivel de dominio de la habilidad
     */
    @Schema(
            description = "Nivel de dominio de la habilidad",
            example = "EXPERTO",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = {"BASICO", "INTERMEDIO", "AVANZADO", "EXPERTO"}
    )
    @NotNull(message = "El nivel de habilidad es obligatorio")
    private NivelHabilidad nivel;
}
