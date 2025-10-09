package com.udeajobs.profile.profile_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad principal que representa el perfil completo de un usuario en MongoDB.
 * Contiene toda la información profesional incluyendo experiencia, educación,
 * habilidades y portafolio.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "profiles")
public class Profile {

    /**
     * Identificador único del perfil, corresponde al userId de la Célula 1
     */
    @Id
    private String id;

    /**
     * Información personal básica del usuario
     */
    private InformacionPersonal informacionPersonal;

    /**
     * Lista de habilidades técnicas y blandas
     */
    @Builder.Default
    private List<Habilidad> habilidades = new ArrayList<>();

    /**
     * Lista de experiencias laborales
     */
    @Builder.Default
    private List<ExperienciaLaboral> experienciaLaboral = new ArrayList<>();

    /**
     * Lista de formación académica
     */
    @Builder.Default
    private List<Educacion> educacion = new ArrayList<>();

    /**
     * Lista de proyectos del portafolio
     */
    @Builder.Default
    private List<Portafolio> portafolio = new ArrayList<>();

    /**
     * Metadatos del perfil
     */
    private Metadata metadata;
}

