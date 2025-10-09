package com.udeajobs.profile.profile_service.service;

import com.udeajobs.profile.profile_service.dto.request.*;
import com.udeajobs.profile.profile_service.dto.response.*;
import com.udeajobs.profile.profile_service.entity.*;
import com.udeajobs.profile.profile_service.exception.ProfileNotFoundException;
import com.udeajobs.profile.profile_service.exception.ResourceNotFoundException;
import com.udeajobs.profile.profile_service.mapper.ProfileMapper;
import com.udeajobs.profile.profile_service.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementación del servicio de perfiles con toda la lógica de negocio.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfile(String userId) {
        log.info("Obteniendo perfil para el usuario: {}", userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        log.debug("Perfil encontrado para el usuario: {}", userId);
        return profileMapper.toProfileResponse(profile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public InformacionPersonalResponse updateInformacionPersonal(String userId, InformacionPersonalRequest request) {
        log.info("Actualizando información personal para el usuario: {}", userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        InformacionPersonal informacionPersonal = profileMapper.toInformacionPersonal(request);
        profile.setInformacionPersonal(informacionPersonal);

        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Información personal actualizada exitosamente para el usuario: {}", userId);
        return profileMapper.toInformacionPersonalResponse(informacionPersonal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ExperienciaLaboralResponse addExperienciaLaboral(String userId, ExperienciaLaboralRequest request) {
        log.info("Añadiendo experiencia laboral para el usuario: {}", userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        String experienceId = UUID.randomUUID().toString();
        ExperienciaLaboral experiencia = profileMapper.toExperienciaLaboral(request, experienceId);

        profile.getExperienciaLaboral().add(experiencia);
        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Experiencia laboral añadida con ID: {} para el usuario: {}", experienceId, userId);
        return profileMapper.toExperienciaLaboralResponse(experiencia);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ExperienciaLaboralResponse updateExperienciaLaboral(String userId, String experienceId,
                                                                ExperienciaLaboralRequest request) {
        log.info("Actualizando experiencia laboral {} para el usuario: {}", experienceId, userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        ExperienciaLaboral experiencia = profile.getExperienciaLaboral().stream()
                .filter(exp -> exp.getId().equals(experienceId))
                .findFirst()
                .orElseThrow(() -> ResourceNotFoundException.forResource("Experiencia laboral", experienceId, userId));

        // Actualizar campos
        experiencia.setEmpresa(request.getEmpresa());
        experiencia.setPuesto(request.getPuesto());
        experiencia.setFechaInicio(request.getFechaInicio());
        experiencia.setFechaFin(request.getFechaFin());
        experiencia.setDescripcion(request.getDescripcion());

        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Experiencia laboral {} actualizada para el usuario: {}", experienceId, userId);
        return profileMapper.toExperienciaLaboralResponse(experiencia);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteExperienciaLaboral(String userId, String experienceId) {
        log.info("Eliminando experiencia laboral {} para el usuario: {}", experienceId, userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        boolean removed = profile.getExperienciaLaboral()
                .removeIf(exp -> exp.getId().equals(experienceId));

        if (!removed) {
            throw ResourceNotFoundException.forResource("Experiencia laboral", experienceId, userId);
        }

        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Experiencia laboral {} eliminada para el usuario: {}", experienceId, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public HabilidadResponse addHabilidad(String userId, HabilidadRequest request) {
        log.info("Añadiendo habilidad para el usuario: {}", userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        String skillId = UUID.randomUUID().toString();
        Habilidad habilidad = profileMapper.toHabilidad(request, skillId);

        profile.getHabilidades().add(habilidad);
        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Habilidad añadida con ID: {} para el usuario: {}", skillId, userId);
        return profileMapper.toHabilidadResponse(habilidad);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteHabilidad(String userId, String skillId) {
        log.info("Eliminando habilidad {} para el usuario: {}", skillId, userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        boolean removed = profile.getHabilidades()
                .removeIf(skill -> skill.getId().equals(skillId));

        if (!removed) {
            throw ResourceNotFoundException.forResource("Habilidad", skillId, userId);
        }

        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Habilidad {} eliminada para el usuario: {}", skillId, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public EducacionResponse addEducacion(String userId, EducacionRequest request) {
        log.info("Añadiendo educación para el usuario: {}", userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        String educationId = UUID.randomUUID().toString();
        Educacion educacion = profileMapper.toEducacion(request, educationId);

        profile.getEducacion().add(educacion);
        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Educación añadida con ID: {} para el usuario: {}", educationId, userId);
        return profileMapper.toEducacionResponse(educacion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public EducacionResponse updateEducacion(String userId, String educationId, EducacionRequest request) {
        log.info("Actualizando educación {} para el usuario: {}", educationId, userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        Educacion educacion = profile.getEducacion().stream()
                .filter(edu -> edu.getId().equals(educationId))
                .findFirst()
                .orElseThrow(() -> ResourceNotFoundException.forResource("Educación", educationId, userId));

        // Actualizar campos
        educacion.setInstitucion(request.getInstitucion());
        educacion.setTitulo(request.getTitulo());
        educacion.setFechaFin(request.getFechaFin());

        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Educación {} actualizada para el usuario: {}", educationId, userId);
        return profileMapper.toEducacionResponse(educacion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteEducacion(String userId, String educationId) {
        log.info("Eliminando educación {} para el usuario: {}", educationId, userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        boolean removed = profile.getEducacion()
                .removeIf(edu -> edu.getId().equals(educationId));

        if (!removed) {
            throw ResourceNotFoundException.forResource("Educación", educationId, userId);
        }

        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Educación {} eliminada para el usuario: {}", educationId, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public PortafolioResponse addPortafolio(String userId, PortafolioRequest request) {
        log.info("Añadiendo proyecto al portafolio para el usuario: {}", userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        String portfolioId = UUID.randomUUID().toString();
        Portafolio portafolio = profileMapper.toPortafolio(request, portfolioId);

        profile.getPortafolio().add(portafolio);
        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Proyecto añadido al portafolio con ID: {} para el usuario: {}", portfolioId, userId);
        return profileMapper.toPortafolioResponse(portafolio);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public PortafolioResponse updatePortafolio(String userId, String portfolioId, PortafolioRequest request) {
        log.info("Actualizando proyecto {} del portafolio para el usuario: {}", portfolioId, userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        Portafolio portafolio = profile.getPortafolio().stream()
                .filter(port -> port.getId().equals(portfolioId))
                .findFirst()
                .orElseThrow(() -> ResourceNotFoundException.forResource("Portafolio", portfolioId, userId));

        // Actualizar campos
        portafolio.setTitulo(request.getTitulo());
        portafolio.setDescripcion(request.getDescripcion());
        portafolio.setUrl(request.getUrl());
        portafolio.setDocumentoUrl(request.getDocumentoUrl());

        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Proyecto {} del portafolio actualizado para el usuario: {}", portfolioId, userId);
        return profileMapper.toPortafolioResponse(portafolio);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deletePortafolio(String userId, String portfolioId) {
        log.info("Eliminando proyecto {} del portafolio para el usuario: {}", portfolioId, userId);

        Profile profile = profileRepository.findById(userId)
                .orElseThrow(() -> ProfileNotFoundException.forUserId(userId));

        boolean removed = profile.getPortafolio()
                .removeIf(port -> port.getId().equals(portfolioId));

        if (!removed) {
            throw ResourceNotFoundException.forResource("Portafolio", portfolioId, userId);
        }

        updateMetadata(profile);
        profileRepository.save(profile);

        log.info("Proyecto {} del portafolio eliminado para el usuario: {}", portfolioId, userId);
    }

    /**
     * Actualiza los metadatos del perfil incluyendo la fecha de última actualización
     * y el estado de completitud del perfil.
     *
     * @param profile perfil a actualizar
     */
    private void updateMetadata(Profile profile) {
        if (profile.getMetadata() == null) {
            profile.setMetadata(Metadata.builder()
                    .fechaCreacion(LocalDateTime.now())
                    .build());
        }

        profile.getMetadata().setUltimaActualizacion(LocalDateTime.now());
        profile.getMetadata().setPerfilCompleto(isProfileComplete(profile));

        log.debug("Metadatos actualizados para el perfil. PerfilCompleto: {}",
                profile.getMetadata().getPerfilCompleto());
    }

    /**
     * Verifica si un perfil está completo.
     * Un perfil se considera completo si tiene información personal,
     * al menos una habilidad y al menos una experiencia laboral.
     *
     * @param profile perfil a verificar
     * @return true si el perfil está completo, false en caso contrario
     */
    private boolean isProfileComplete(Profile profile) {
        boolean hasPersonalInfo = profile.getInformacionPersonal() != null &&
                profile.getInformacionPersonal().getNombreCompleto() != null &&
                profile.getInformacionPersonal().getTituloProfesional() != null;

        boolean hasSkills = profile.getHabilidades() != null && !profile.getHabilidades().isEmpty();

        boolean hasExperience = profile.getExperienciaLaboral() != null &&
                !profile.getExperienciaLaboral().isEmpty();

        return hasPersonalInfo && hasSkills && hasExperience;
    }
}

