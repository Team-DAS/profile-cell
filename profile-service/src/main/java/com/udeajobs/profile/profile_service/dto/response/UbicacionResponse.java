package com.udeajobs.profile.profile_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para la ubicación del usuario.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UbicacionResponse {

    /**
     * Ciudad donde se encuentra el usuario
     */
    private String ciudad;

    /**
     * País donde se encuentra el usuario
     */
    private String pais;
}
