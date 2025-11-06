package com.udeajobs.profile.profile_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

/**
 * DTO de request para actualizar la información personal del perfil.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Información personal del perfil del usuario")
public class InformacionPersonalRequest {

    /**
     * Nombre completo del usuario
     */
    @Schema(
            description = "Nombre completo del usuario",
            example = "Juan Pérez Silva",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 3,
            maxLength = 150
    )
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre completo debe tener entre 3 y 150 caracteres")
    private String nombreCompleto;

    /**
     * Título profesional del usuario
     */
    @Schema(
            description = "Título o rol profesional del usuario",
            example = "Desarrollador de Software Senior",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 3,
            maxLength = 200
    )
    @NotBlank(message = "El título profesional es obligatorio")
    @Size(min = 3, max = 200, message = "El título profesional debe tener entre 3 y 200 caracteres")
    private String tituloProfesional;

    /**
     * Resumen o descripción del perfil profesional
     */
    @Schema(
            description = "Resumen o descripción profesional del usuario",
            example = "Más de 10 años de experiencia en desarrollo backend con Java, Spring Boot y arquitecturas de microservicios.",
            maxLength = 2000
    )
    @Size(max = 2000, message = "El resumen no puede exceder los 2000 caracteres")
    private String resumen;

    /**
     * Ubicación geográfica del usuario
     */
    @Schema(description = "Ubicación geográfica del usuario")
    @Valid
    private UbicacionRequest ubicacion;

    /**
     * URL de la foto de perfil
     */
    @Schema(
            description = "URL de la foto de perfil del usuario (gestionada por el file-service)",
            example = "https://storage.googleapis.com/bucket/profiles/foto_juan.jpg"
    )
    @URL(message = "La URL de la foto debe ser válida")
    private String fotoUrl;
}
