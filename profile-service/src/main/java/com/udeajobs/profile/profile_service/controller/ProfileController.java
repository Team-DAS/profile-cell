package com.udeajobs.profile.profile_service.controller;

import com.udeajobs.profile.profile_service.dto.request.*;
import com.udeajobs.profile.profile_service.dto.response.*;
import com.udeajobs.profile.profile_service.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para gestionar los perfiles de usuario.
 * Expone endpoints para operaciones CRUD sobre perfiles y sus componentes.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Profiles", description = "API para gestión completa de perfiles de usuario")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * Obtiene el perfil completo de un usuario.
     *
     * @param userId identificador del usuario
     * @return perfil completo con código 200
     */
    @Operation(
            summary = "Obtener perfil completo",
            description = "Recupera toda la información del perfil de un usuario específico, incluyendo información personal, habilidades, experiencia laboral, educación y portafolio."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Perfil recuperado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil no encontrado para el userId especificado",
                    content = @Content
            )
    })
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId) {
        log.info("GET /api/v1/profiles/{} - Obteniendo perfil", userId);
        ProfileResponse profile = profileService.getProfile(userId);
        return ResponseEntity.ok(profile);
    }

    /**
     * Actualiza la información personal del perfil.
     *
     * @param userId identificador del usuario
     * @param request datos de información personal
     * @return información personal actualizada con código 200
     */
    @Operation(
            summary = "Actualizar información personal",
            description = "Actualiza la sección de información personal del perfil (nombre completo, título profesional, resumen, ubicación y foto). Esta operación reemplaza toda la sección."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Información personal actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = InformacionPersonalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil no encontrado",
                    content = @Content
            )
    })
    @PutMapping("/{userId}/personal-info")
    public ResponseEntity<InformacionPersonalResponse> updatePersonalInfo(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de información personal a actualizar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = InformacionPersonalRequest.class))
            )
            @Valid @RequestBody InformacionPersonalRequest request) {

        log.info("PUT /api/v1/profiles/{}/personal-info - Actualizando información personal", userId);
        InformacionPersonalResponse response = profileService.updateInformacionPersonal(userId, request);
        return ResponseEntity.ok(response);
    }

    // ==================== EXPERIENCIA LABORAL ====================

    /**
     * Añade una nueva experiencia laboral al perfil.
     *
     * @param userId identificador del usuario
     * @param request datos de la experiencia
     * @return experiencia creada con código 201
     */
    @Operation(
            summary = "Agregar experiencia laboral",
            description = "Añade una nueva entrada de experiencia laboral al historial profesional del usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Experiencia laboral creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExperienciaLaboralResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil no encontrado",
                    content = @Content
            )
    })
    @PostMapping("/{userId}/experience")
    public ResponseEntity<ExperienciaLaboralResponse> addExperience(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la experiencia laboral a agregar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ExperienciaLaboralRequest.class))
            )
            @Valid @RequestBody ExperienciaLaboralRequest request) {

        log.info("POST /api/v1/profiles/{}/experience - Añadiendo experiencia laboral", userId);
        ExperienciaLaboralResponse response = profileService.addExperienciaLaboral(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza una experiencia laboral existente.
     *
     * @param userId identificador del usuario
     * @param experienceId identificador de la experiencia
     * @param request datos actualizados
     * @return experiencia actualizada con código 200
     */
    @Operation(
            summary = "Actualizar experiencia laboral",
            description = "Modifica los datos de una experiencia laboral existente en el perfil del usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Experiencia laboral actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExperienciaLaboralResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil o experiencia laboral no encontrada",
                    content = @Content
            )
    })
    @PutMapping("/{userId}/experience/{experienceId}")
    public ResponseEntity<ExperienciaLaboralResponse> updateExperience(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @Parameter(description = "Identificador único de la experiencia laboral", required = true, example = "exp-123")
            @PathVariable String experienceId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados de la experiencia laboral",
                    required = true,
                    content = @Content(schema = @Schema(implementation = ExperienciaLaboralRequest.class))
            )
            @Valid @RequestBody ExperienciaLaboralRequest request) {

        log.info("PUT /api/v1/profiles/{}/experience/{} - Actualizando experiencia", userId, experienceId);
        ExperienciaLaboralResponse response = profileService.updateExperienciaLaboral(userId, experienceId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una experiencia laboral del perfil.
     *
     * @param userId identificador del usuario
     * @param experienceId identificador de la experiencia
     * @return respuesta vacía con código 204
     */
    @Operation(
            summary = "Eliminar experiencia laboral",
            description = "Elimina una entrada de experiencia laboral del historial profesional del usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Experiencia laboral eliminada exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil o experiencia laboral no encontrada",
                    content = @Content
            )
    })
    @DeleteMapping("/{userId}/experience/{experienceId}")
    public ResponseEntity<Void> deleteExperience(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @Parameter(description = "Identificador único de la experiencia laboral", required = true, example = "exp-123")
            @PathVariable String experienceId) {

        log.info("DELETE /api/v1/profiles/{}/experience/{} - Eliminando experiencia", userId, experienceId);
        profileService.deleteExperienciaLaboral(userId, experienceId);
        return ResponseEntity.noContent().build();
    }

    // ==================== HABILIDADES ====================

    /**
     * Añade una nueva habilidad al perfil.
     *
     * @param userId identificador del usuario
     * @param request datos de la habilidad
     * @return habilidad creada con código 201
     */
    @Operation(
            summary = "Agregar habilidad",
            description = "Añade una nueva habilidad al conjunto de competencias del usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Habilidad creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = HabilidadResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil no encontrado",
                    content = @Content
            )
    })
    @PostMapping("/{userId}/skills")
    public ResponseEntity<HabilidadResponse> addSkill(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la habilidad a agregar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = HabilidadRequest.class))
            )
            @Valid @RequestBody HabilidadRequest request) {

        log.info("POST /api/v1/profiles/{}/skills - Añadiendo habilidad", userId);
        HabilidadResponse response = profileService.addHabilidad(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Elimina una habilidad del perfil.
     *
     * @param userId identificador del usuario
     * @param skillId identificador de la habilidad
     * @return respuesta vacía con código 204
     */
    @Operation(
            summary = "Eliminar habilidad",
            description = "Elimina una habilidad del conjunto de competencias del usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Habilidad eliminada exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil o habilidad no encontrada",
                    content = @Content
            )
    })
    @DeleteMapping("/{userId}/skills/{skillId}")
    public ResponseEntity<Void> deleteSkill(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @Parameter(description = "Identificador único de la habilidad", required = true, example = "skill-123")
            @PathVariable String skillId) {

        log.info("DELETE /api/v1/profiles/{}/skills/{} - Eliminando habilidad", userId, skillId);
        profileService.deleteHabilidad(userId, skillId);
        return ResponseEntity.noContent().build();
    }

    // ==================== EDUCACIÓN ====================

    /**
     * Añade una nueva educación al perfil.
     *
     * @param userId identificador del usuario
     * @param request datos de la educación
     * @return educación creada con código 201
     */
    @Operation(
            summary = "Agregar educación",
            description = "Añade una nueva entrada de educación al historial académico del usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Educación creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EducacionResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil no encontrado",
                    content = @Content
            )
    })
    @PostMapping("/{userId}/education")
    public ResponseEntity<EducacionResponse> addEducation(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos de la educación a agregar",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EducacionRequest.class))
            )
            @Valid @RequestBody EducacionRequest request) {

        log.info("POST /api/v1/profiles/{}/education - Añadiendo educación", userId);
        EducacionResponse response = profileService.addEducacion(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza una educación existente.
     *
     * @param userId identificador del usuario
     * @param educationId identificador de la educación
     * @param request datos actualizados
     * @return educación actualizada con código 200
     */
    @Operation(
            summary = "Actualizar educación",
            description = "Modifica los datos de una entrada de educación existente en el perfil del usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Educación actualizada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EducacionResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil o educación no encontrada",
                    content = @Content
            )
    })
    @PutMapping("/{userId}/education/{educationId}")
    public ResponseEntity<EducacionResponse> updateEducation(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @Parameter(description = "Identificador único de la educación", required = true, example = "edu-123")
            @PathVariable String educationId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados de la educación",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EducacionRequest.class))
            )
            @Valid @RequestBody EducacionRequest request) {

        log.info("PUT /api/v1/profiles/{}/education/{} - Actualizando educación", userId, educationId);
        EducacionResponse response = profileService.updateEducacion(userId, educationId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una educación del perfil.
     *
     * @param userId identificador del usuario
     * @param educationId identificador de la educación
     * @return respuesta vacía con código 204
     */
    @Operation(
            summary = "Eliminar educación",
            description = "Elimina una entrada de educación del historial académico del usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Educación eliminada exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil o educación no encontrada",
                    content = @Content
            )
    })
    @DeleteMapping("/{userId}/education/{educationId}")
    public ResponseEntity<Void> deleteEducation(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @Parameter(description = "Identificador único de la educación", required = true, example = "edu-123")
            @PathVariable String educationId) {

        log.info("DELETE /api/v1/profiles/{}/education/{} - Eliminando educación", userId, educationId);
        profileService.deleteEducacion(userId, educationId);
        return ResponseEntity.noContent().build();
    }

    // ==================== PORTAFOLIO ====================

    /**
     * Añade un nuevo proyecto al portafolio.
     *
     * @param userId identificador del usuario
     * @param request datos del proyecto
     * @return proyecto creado con código 201
     */
    @Operation(
            summary = "Agregar proyecto al portafolio",
            description = "Añade un nuevo proyecto al portafolio del usuario con su título, descripción, URL y documentos."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Proyecto agregado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PortafolioResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil no encontrado",
                    content = @Content
            )
    })
    @PostMapping("/{userId}/portfolio")
    public ResponseEntity<PortafolioResponse> addPortfolio(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del proyecto a agregar al portafolio",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PortafolioRequest.class))
            )
            @Valid @RequestBody PortafolioRequest request) {

        log.info("POST /api/v1/profiles/{}/portfolio - Añadiendo proyecto al portafolio", userId);
        PortafolioResponse response = profileService.addPortafolio(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza un proyecto del portafolio.
     *
     * @param userId identificador del usuario
     * @param portfolioId identificador del proyecto
     * @param request datos actualizados
     * @return proyecto actualizado con código 200
     */
    @Operation(
            summary = "Actualizar proyecto del portafolio",
            description = "Modifica los datos de un proyecto existente en el portafolio del usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Proyecto actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PortafolioResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil o proyecto no encontrado",
                    content = @Content
            )
    })
    @PutMapping("/{userId}/portfolio/{portfolioId}")
    public ResponseEntity<PortafolioResponse> updatePortfolio(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @Parameter(description = "Identificador único del proyecto", required = true, example = "port-123")
            @PathVariable String portfolioId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos actualizados del proyecto",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PortafolioRequest.class))
            )
            @Valid @RequestBody PortafolioRequest request) {

        log.info("PUT /api/v1/profiles/{}/portfolio/{} - Actualizando proyecto", userId, portfolioId);
        PortafolioResponse response = profileService.updatePortafolio(userId, portfolioId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un proyecto del portafolio.
     *
     * @param userId identificador del usuario
     * @param portfolioId identificador del proyecto
     * @return respuesta vacía con código 204
     */
    @Operation(
            summary = "Eliminar proyecto del portafolio",
            description = "Elimina un proyecto del portafolio del usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Proyecto eliminado exitosamente",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Perfil o proyecto no encontrado",
                    content = @Content
            )
    })
    @DeleteMapping("/{userId}/portfolio/{portfolioId}")
    public ResponseEntity<Void> deletePortfolio(
            @Parameter(description = "Identificador único del usuario", required = true, example = "usuario-uuid-abc-123")
            @PathVariable String userId,
            @Parameter(description = "Identificador único del proyecto", required = true, example = "port-123")
            @PathVariable String portfolioId) {

        log.info("DELETE /api/v1/profiles/{}/portfolio/{} - Eliminando proyecto", userId, portfolioId);
        profileService.deletePortafolio(userId, portfolioId);
        return ResponseEntity.noContent().build();
    }
}

