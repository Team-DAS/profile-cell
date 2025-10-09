package com.udeajobs.profile.profile_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad embebida que representa la ubicación geográfica de un usuario.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion {

    /**
     * Ciudad donde se encuentra el usuario
     */
    private String ciudad;

    /**
     * País donde se encuentra el usuario
     */
    private String pais;
}
