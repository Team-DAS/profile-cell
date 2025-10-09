package com.udeajobs.profile.profile_service.dto.request;

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
public class InformacionPersonalRequest {

    /**
     * Nombre completo del usuario
     */
    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre completo debe tener entre 3 y 150 caracteres")
    private String nombreCompleto;

    /**
     * Título profesional del usuario
     */
    @NotBlank(message = "El título profesional es obligatorio")
    @Size(min = 3, max = 200, message = "El título profesional debe tener entre 3 y 200 caracteres")
    private String tituloProfesional;

    /**
     * Resumen o descripción del perfil profesional
     */
    @Size(max = 2000, message = "El resumen no puede exceder los 2000 caracteres")
    private String resumen;

    /**
     * Ubicación geográfica del usuario
     */
    @Valid
    private UbicacionRequest ubicacion;

    /**
     * URL de la foto de perfil
     */
    @URL(message = "La URL de la foto debe ser válida")
    private String fotoUrl;
}
