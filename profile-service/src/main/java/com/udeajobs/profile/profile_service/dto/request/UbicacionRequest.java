package com.udeajobs.profile.profile_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de request para la ubicación del usuario.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ubicación geográfica del usuario")
public class UbicacionRequest {

    /**
     * Ciudad donde se encuentra el usuario
     */
    @Schema(
            description = "Ciudad donde se encuentra el usuario",
            example = "Medellín",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 2,
            maxLength = 100
    )
    @NotBlank(message = "La ciudad es obligatoria")
    @Size(min = 2, max = 100, message = "La ciudad debe tener entre 2 y 100 caracteres")
    private String ciudad;

    /**
     * País donde se encuentra el usuario
     */
    @Schema(
            description = "País donde se encuentra el usuario",
            example = "Colombia",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 2,
            maxLength = 100
    )
    @NotBlank(message = "El país es obligatorio")
    @Size(min = 2, max = 100, message = "El país debe tener entre 2 y 100 caracteres")
    private String pais;
}
