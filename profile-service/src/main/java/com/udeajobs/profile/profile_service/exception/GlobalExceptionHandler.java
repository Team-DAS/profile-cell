package com.udeajobs.profile.profile_service.exception;

import com.udeajobs.profile.profile_service.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones para toda la aplicación.
 * Intercepta y procesa las excepciones, devolviendo respuestas HTTP estructuradas.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja las excepciones cuando no se encuentra un perfil.
     *
     * @param ex excepción lanzada
     * @param request petición HTTP
     * @return respuesta con código 404
     */
    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProfileNotFoundException(
            ProfileNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Perfil no encontrado: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Maneja las excepciones cuando no se encuentra un recurso específico.
     *
     * @param ex excepción lanzada
     * @param request petición HTTP
     * @return respuesta con código 404
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        log.warn("Recurso no encontrado: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Maneja las excepciones de datos inválidos.
     *
     * @param ex excepción lanzada
     * @param request petición HTTP
     * @return respuesta con código 400
     */
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(
            InvalidDataException ex,
            HttpServletRequest request) {

        log.error("Datos inválidos: {}", ex.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Maneja las excepciones de validación de argumentos de métodos.
     * Captura errores de validación de @Valid en los DTOs.
     *
     * @param ex excepción de validación
     * @param request petición HTTP
     * @return respuesta con código 400 y lista de errores de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        log.warn("Error de validación en la petición: {}", request.getRequestURI());

        List<ErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ErrorResponse.ValidationError.builder()
                        .field(error.getField())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message("Error de validación en los datos proporcionados")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .errors(validationErrors)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Maneja excepciones genéricas no capturadas por otros manejadores.
     *
     * @param ex excepción lanzada
     * @param request petición HTTP
     * @return respuesta con código 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        log.error("Error interno del servidor: ", ex);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Error interno del servidor. Por favor, intente más tarde.")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

