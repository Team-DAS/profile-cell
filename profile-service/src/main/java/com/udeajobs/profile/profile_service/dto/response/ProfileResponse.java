package com.udeajobs.profile.profile_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de respuesta para el perfil completo del usuario.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    /**
     * Identificador único del perfil (userId)
     */
    private String id;

    /**
     * Información personal del usuario
     */
    private InformacionPersonalResponse informacionPersonal;

    /**
     * Lista de habilidades
     */
    private List<HabilidadResponse> habilidades;

    /**
     * Lista de experiencias laborales
     */
    private List<ExperienciaLaboralResponse> experienciaLaboral;

    /**
     * Lista de formación académica
     */
    private List<EducacionResponse> educacion;

    /**
     * Lista de proyectos del portafolio
     */
    private List<PortafolioResponse> portafolio;

    /**
     * Metadatos del perfil
     */
    private MetadataResponse metadata;
}

