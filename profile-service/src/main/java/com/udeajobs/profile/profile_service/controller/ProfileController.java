package com.udeajobs.profile.profile_service.controller;

import com.udeajobs.profile.profile_service.dto.request.*;
import com.udeajobs.profile.profile_service.dto.response.*;
import com.udeajobs.profile.profile_service.service.ProfileService;
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
@RequestMapping("/api/v1/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    /**
     * Obtiene el perfil completo de un usuario.
     *
     * @param userId identificador del usuario
     * @return perfil completo con código 200
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable String userId) {
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
    @PutMapping("/{userId}/personal-info")
    public ResponseEntity<InformacionPersonalResponse> updatePersonalInfo(
            @PathVariable String userId,
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
    @PostMapping("/{userId}/experience")
    public ResponseEntity<ExperienciaLaboralResponse> addExperience(
            @PathVariable String userId,
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
    @PutMapping("/{userId}/experience/{experienceId}")
    public ResponseEntity<ExperienciaLaboralResponse> updateExperience(
            @PathVariable String userId,
            @PathVariable String experienceId,
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
    @DeleteMapping("/{userId}/experience/{experienceId}")
    public ResponseEntity<Void> deleteExperience(
            @PathVariable String userId,
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
    @PostMapping("/{userId}/skills")
    public ResponseEntity<HabilidadResponse> addSkill(
            @PathVariable String userId,
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
    @DeleteMapping("/{userId}/skills/{skillId}")
    public ResponseEntity<Void> deleteSkill(
            @PathVariable String userId,
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
    @PostMapping("/{userId}/education")
    public ResponseEntity<EducacionResponse> addEducation(
            @PathVariable String userId,
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
    @PutMapping("/{userId}/education/{educationId}")
    public ResponseEntity<EducacionResponse> updateEducation(
            @PathVariable String userId,
            @PathVariable String educationId,
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
    @DeleteMapping("/{userId}/education/{educationId}")
    public ResponseEntity<Void> deleteEducation(
            @PathVariable String userId,
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
    @PostMapping("/{userId}/portfolio")
    public ResponseEntity<PortafolioResponse> addPortfolio(
            @PathVariable String userId,
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
    @PutMapping("/{userId}/portfolio/{portfolioId}")
    public ResponseEntity<PortafolioResponse> updatePortfolio(
            @PathVariable String userId,
            @PathVariable String portfolioId,
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
    @DeleteMapping("/{userId}/portfolio/{portfolioId}")
    public ResponseEntity<Void> deletePortfolio(
            @PathVariable String userId,
            @PathVariable String portfolioId) {

        log.info("DELETE /api/v1/profiles/{}/portfolio/{} - Eliminando proyecto", userId, portfolioId);
        profileService.deletePortafolio(userId, portfolioId);
        return ResponseEntity.noContent().build();
    }
}

