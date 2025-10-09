package com.udeajobs.profile.profile_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad embebida que representa un elemento del portafolio de proyectos del usuario.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Portafolio {

    /**
     * Identificador único del elemento de portafolio dentro del perfil
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
     * URL del proyecto (repositorio, sitio web, etc.)
     */
    private String url;

    /**
     * URL del documento o archivo adicional almacenado en el file-service
     */
    private String documentoUrl;
}
