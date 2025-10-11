package com.udeajobs.profile_cell.file_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO estandarizado para respuestas de error en el sistema.
 * Proporciona información consistente sobre errores a los clientes.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Timestamp de cuando ocurrió el error
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Código de estado HTTP
     */
    private int status;

    /**
     * Nombre del error HTTP (ej. "Bad Request", "Not Found")
     */
    private String error;

    /**
     * Mensaje descriptivo del error
     */
    private String message;

    /**
     * Ruta de la petición que generó el error
     */
    private String path;
}

