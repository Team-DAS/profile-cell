package com.udeajobs.profile.profile_service.service;

import com.udeajobs.profile.profile_service.dto.request.*;
import com.udeajobs.profile.profile_service.dto.response.*;
import com.udeajobs.profile.profile_service.events.CuentaVerificadaEvent;

/**
 * Interfaz del servicio de perfiles que define las operaciones de negocio.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
public interface ProfileService {

    /**
     * Creates a base user profile for event usage.
     *
     * @param event event data
     * @return created base profile
     */
    ProfileResponse createBaseUser(CuentaVerificadaEvent event);

    /**
     * Obtiene el perfil completo de un usuario.
     *
     * @param userId identificador del usuario
     * @return perfil completo
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     */
    ProfileResponse getProfile(String userId);

    /**
     * Actualiza la información personal del perfil.
     *
     * @param userId identificador del usuario
     * @param request datos de información personal
     * @return información personal actualizada
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     */
    InformacionPersonalResponse updateInformacionPersonal(String userId, InformacionPersonalRequest request);

    /**
     * Añade una nueva experiencia laboral al perfil.
     *
     * @param userId identificador del usuario
     * @param request datos de la experiencia
     * @return experiencia creada con su ID
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     */
    ExperienciaLaboralResponse addExperienciaLaboral(String userId, ExperienciaLaboralRequest request);

    /**
     * Actualiza una experiencia laboral existente.
     *
     * @param userId identificador del usuario
     * @param experienceId identificador de la experiencia
     * @param request datos actualizados
     * @return experiencia actualizada
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     * @throws com.udeajobs.profile.profile_service.exception.ResourceNotFoundException si no existe la experiencia
     */
    ExperienciaLaboralResponse updateExperienciaLaboral(String userId, String experienceId, ExperienciaLaboralRequest request);

    /**
     * Elimina una experiencia laboral del perfil.
     *
     * @param userId identificador del usuario
     * @param experienceId identificador de la experiencia
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     * @throws com.udeajobs.profile.profile_service.exception.ResourceNotFoundException si no existe la experiencia
     */
    void deleteExperienciaLaboral(String userId, String experienceId);

    /**
     * Añade una nueva habilidad al perfil.
     *
     * @param userId identificador del usuario
     * @param request datos de la habilidad
     * @return habilidad creada con su ID
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     */
    HabilidadResponse addHabilidad(String userId, HabilidadRequest request);

    /**
     * Elimina una habilidad del perfil.
     *
     * @param userId identificador del usuario
     * @param skillId identificador de la habilidad
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     * @throws com.udeajobs.profile.profile_service.exception.ResourceNotFoundException si no existe la habilidad
     */
    void deleteHabilidad(String userId, String skillId);

    /**
     * Añade una nueva educación al perfil.
     *
     * @param userId identificador del usuario
     * @param request datos de la educación
     * @return educación creada con su ID
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     */
    EducacionResponse addEducacion(String userId, EducacionRequest request);

    /**
     * Actualiza una educación existente.
     *
     * @param userId identificador del usuario
     * @param educationId identificador de la educación
     * @param request datos actualizados
     * @return educación actualizada
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     * @throws com.udeajobs.profile.profile_service.exception.ResourceNotFoundException si no existe la educación
     */
    EducacionResponse updateEducacion(String userId, String educationId, EducacionRequest request);

    /**
     * Elimina una educación del perfil.
     *
     * @param userId identificador del usuario
     * @param educationId identificador de la educación
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     * @throws com.udeajobs.profile.profile_service.exception.ResourceNotFoundException si no existe la educación
     */
    void deleteEducacion(String userId, String educationId);

    /**
     * Añade un nuevo proyecto al portafolio.
     *
     * @param userId identificador del usuario
     * @param request datos del proyecto
     * @return proyecto creado con su ID
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     */
    PortafolioResponse addPortafolio(String userId, PortafolioRequest request);

    /**
     * Actualiza un proyecto del portafolio.
     *
     * @param userId identificador del usuario
     * @param portfolioId identificador del proyecto
     * @param request datos actualizados
     * @return proyecto actualizado
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     * @throws com.udeajobs.profile.profile_service.exception.ResourceNotFoundException si no existe el proyecto
     */
    PortafolioResponse updatePortafolio(String userId, String portfolioId, PortafolioRequest request);

    /**
     * Elimina un proyecto del portafolio.
     *
     * @param userId identificador del usuario
     * @param portfolioId identificador del proyecto
     * @throws com.udeajobs.profile.profile_service.exception.ProfileNotFoundException si no existe el perfil
     * @throws com.udeajobs.profile.profile_service.exception.ResourceNotFoundException si no existe el proyecto
     */
    void deletePortafolio(String userId, String portfolioId);
}

