package com.udeajobs.profile.profile_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de respuesta para representar errores de manera estructurada.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    /**
     * Código de estado HTTP
     */
    private Integer status;

    /**
     * Mensaje de error principal
     */
    private String message;

    /**
     * Timestamp del error
     */
    private LocalDateTime timestamp;

    /**
     * Path de la petición que generó el error
     */
    private String path;

    /**
     * Lista de errores de validación detallados
     */
    private List<ValidationError> errors;

    /**
     * Clase interna para representar errores de validación específicos
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ValidationError {
        /**
         * Campo que falló la validación
         */
        private String field;

        /**
         * Mensaje de error específico del campo
         */
        private String message;
    }
}

