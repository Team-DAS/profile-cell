package com.udeajobs.profile.profile_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para un elemento del portafolio.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PortafolioResponse {

    /**
     * Identificador único del elemento de portafolio
     */
    private String id;

    /**
     * Título del proyecto
     */
    private String titulo;

    /**
     * Descripción del proyecto
     */
    private String descripcion;

    /**
     * URL del proyecto
     */
    private String url;

    /**
     * URL del documento o archivo adicional
     */
    private String documentoUrl;
}
