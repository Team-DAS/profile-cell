package com.udeajobs.profile.profile_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

/**
 * DTO de request para crear o actualizar un elemento del portafolio.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortafolioRequest {

    /**
     * Título del proyecto
     */
    @NotBlank(message = "El título del proyecto es obligatorio")
    @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
    private String titulo;

    /**
     * Descripción del proyecto
     */
    @Size(max = 2000, message = "La descripción no puede exceder los 2000 caracteres")
    private String descripcion;

    /**
     * URL del proyecto
     */
    @URL(message = "La URL del proyecto debe ser válida")
    private String url;

    /**
     * URL del documento o archivo adicional
     */
    @URL(message = "La URL del documento debe ser válida")
    private String documentoUrl;
}
