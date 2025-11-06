package com.udeajobs.profile.profile_service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Proyecto o trabajo del portafolio del usuario")
public class PortafolioRequest {

    /**
     * Título del proyecto
     */
    @Schema(
            description = "Título del proyecto",
            example = "Sistema de E-commerce",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 3,
            maxLength = 200
    )
    @NotBlank(message = "El título del proyecto es obligatorio")
    @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
    private String titulo;

    /**
     * Descripción del proyecto
     */
    @Schema(
            description = "Descripción detallada del proyecto",
            example = "Plataforma de ventas desarrollada con Django y React, con integración de pasarela de pagos.",
            maxLength = 2000
    )
    @Size(max = 2000, message = "La descripción no puede exceder los 2000 caracteres")
    private String descripcion;

    /**
     * URL del proyecto
     */
    @Schema(
            description = "URL del proyecto (repositorio, sitio web, demo)",
            example = "https://github.com/juanperez/ecommerce"
    )
    @URL(message = "La URL del proyecto debe ser válida")
    private String url;

    /**
     * URL del documento o archivo adicional
     */
    @Schema(
            description = "URL de documentación adicional o caso de estudio (gestionada por el file-service)",
            example = "https://storage.googleapis.com/bucket/portafolios/ecommerce_caso.pdf"
    )
    @URL(message = "La URL del documento debe ser válida")
    private String documentoUrl;
}
