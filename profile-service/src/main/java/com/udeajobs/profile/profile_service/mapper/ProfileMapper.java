package com.udeajobs.profile.profile_service.mapper;

import com.udeajobs.profile.profile_service.dto.request.*;
import com.udeajobs.profile.profile_service.dto.response.*;
import com.udeajobs.profile.profile_service.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper para conversión entre entidades y DTOs.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Component
public class ProfileMapper {

    /**
     * Convierte una entidad Profile a ProfileResponse.
     *
     * @param profile entidad a convertir
     * @return DTO de respuesta
     */
    public ProfileResponse toProfileResponse(Profile profile) {
        if (profile == null) {
            return null;
        }

        return ProfileResponse.builder()
                .id(profile.getId())
                .informacionPersonal(toInformacionPersonalResponse(profile.getInformacionPersonal()))
                .habilidades(profile.getHabilidades() != null ?
                        profile.getHabilidades().stream()
                                .map(this::toHabilidadResponse)
                                .collect(Collectors.toList()) : List.of())
                .experienciaLaboral(profile.getExperienciaLaboral() != null ?
                        profile.getExperienciaLaboral().stream()
                                .map(this::toExperienciaLaboralResponse)
                                .collect(Collectors.toList()) : List.of())
                .educacion(profile.getEducacion() != null ?
                        profile.getEducacion().stream()
                                .map(this::toEducacionResponse)
                                .collect(Collectors.toList()) : List.of())
                .portafolio(profile.getPortafolio() != null ?
                        profile.getPortafolio().stream()
                                .map(this::toPortafolioResponse)
                                .collect(Collectors.toList()) : List.of())
                .metadata(toMetadataResponse(profile.getMetadata()))
                .build();
    }

    /**
     * Convierte InformacionPersonal a InformacionPersonalResponse.
     *
     * @param info entidad a convertir
     * @return DTO de respuesta
     */
    public InformacionPersonalResponse toInformacionPersonalResponse(InformacionPersonal info) {
        if (info == null) {
            return null;
        }

        return InformacionPersonalResponse.builder()
                .nombreCompleto(info.getNombreCompleto())
                .tituloProfesional(info.getTituloProfesional())
                .resumen(info.getResumen())
                .ubicacion(toUbicacionResponse(info.getUbicacion()))
                .fotoUrl(info.getFotoUrl())
                .build();
    }

    /**
     * Convierte Ubicacion a UbicacionResponse.
     *
     * @param ubicacion entidad a convertir
     * @return DTO de respuesta
     */
    public UbicacionResponse toUbicacionResponse(Ubicacion ubicacion) {
        if (ubicacion == null) {
            return null;
        }

        return UbicacionResponse.builder()
                .ciudad(ubicacion.getCiudad())
                .pais(ubicacion.getPais())
                .build();
    }

    /**
     * Convierte Habilidad a HabilidadResponse.
     *
     * @param habilidad entidad a convertir
     * @return DTO de respuesta
     */
    public HabilidadResponse toHabilidadResponse(Habilidad habilidad) {
        if (habilidad == null) {
            return null;
        }

        return HabilidadResponse.builder()
                .id(habilidad.getId())
                .nombre(habilidad.getNombre())
                .nivel(habilidad.getNivel())
                .build();
    }

    /**
     * Convierte ExperienciaLaboral a ExperienciaLaboralResponse.
     *
     * @param experiencia entidad a convertir
     * @return DTO de respuesta
     */
    public ExperienciaLaboralResponse toExperienciaLaboralResponse(ExperienciaLaboral experiencia) {
        if (experiencia == null) {
            return null;
        }

        return ExperienciaLaboralResponse.builder()
                .id(experiencia.getId())
                .empresa(experiencia.getEmpresa())
                .puesto(experiencia.getPuesto())
                .fechaInicio(experiencia.getFechaInicio())
                .fechaFin(experiencia.getFechaFin())
                .descripcion(experiencia.getDescripcion())
                .build();
    }

    /**
     * Convierte Educacion a EducacionResponse.
     *
     * @param educacion entidad a convertir
     * @return DTO de respuesta
     */
    public EducacionResponse toEducacionResponse(Educacion educacion) {
        if (educacion == null) {
            return null;
        }

        return EducacionResponse.builder()
                .id(educacion.getId())
                .institucion(educacion.getInstitucion())
                .titulo(educacion.getTitulo())
                .fechaFin(educacion.getFechaFin())
                .build();
    }

    /**
     * Convierte Portafolio a PortafolioResponse.
     *
     * @param portafolio entidad a convertir
     * @return DTO de respuesta
     */
    public PortafolioResponse toPortafolioResponse(Portafolio portafolio) {
        if (portafolio == null) {
            return null;
        }

        return PortafolioResponse.builder()
                .id(portafolio.getId())
                .titulo(portafolio.getTitulo())
                .descripcion(portafolio.getDescripcion())
                .url(portafolio.getUrl())
                .documentoUrl(portafolio.getDocumentoUrl())
                .build();
    }

    /**
     * Convierte Metadata a MetadataResponse.
     *
     * @param metadata entidad a convertir
     * @return DTO de respuesta
     */
    public MetadataResponse toMetadataResponse(Metadata metadata) {
        if (metadata == null) {
            return null;
        }

        return MetadataResponse.builder()
                .perfilCompleto(metadata.getPerfilCompleto())
                .fechaCreacion(metadata.getFechaCreacion())
                .ultimaActualizacion(metadata.getUltimaActualizacion())
                .build();
    }

    /**
     * Convierte InformacionPersonalRequest a InformacionPersonal.
     *
     * @param request DTO de request
     * @return entidad
     */
    public InformacionPersonal toInformacionPersonal(InformacionPersonalRequest request) {
        if (request == null) {
            return null;
        }

        return InformacionPersonal.builder()
                .nombreCompleto(request.getNombreCompleto())
                .tituloProfesional(request.getTituloProfesional())
                .resumen(request.getResumen())
                .ubicacion(toUbicacion(request.getUbicacion()))
                .fotoUrl(request.getFotoUrl())
                .build();
    }

    /**
     * Convierte UbicacionRequest a Ubicacion.
     *
     * @param request DTO de request
     * @return entidad
     */
    public Ubicacion toUbicacion(UbicacionRequest request) {
        if (request == null) {
            return null;
        }

        return Ubicacion.builder()
                .ciudad(request.getCiudad())
                .pais(request.getPais())
                .build();
    }

    /**
     * Convierte HabilidadRequest a Habilidad con ID generado.
     *
     * @param request DTO de request
     * @param id identificador único
     * @return entidad
     */
    public Habilidad toHabilidad(HabilidadRequest request, String id) {
        if (request == null) {
            return null;
        }

        return Habilidad.builder()
                .id(id)
                .nombre(request.getNombre())
                .nivel(request.getNivel())
                .build();
    }

    /**
     * Convierte ExperienciaLaboralRequest a ExperienciaLaboral con ID generado.
     *
     * @param request DTO de request
     * @param id identificador único
     * @return entidad
     */
    public ExperienciaLaboral toExperienciaLaboral(ExperienciaLaboralRequest request, String id) {
        if (request == null) {
            return null;
        }

        return ExperienciaLaboral.builder()
                .id(id)
                .empresa(request.getEmpresa())
                .puesto(request.getPuesto())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .descripcion(request.getDescripcion())
                .build();
    }

    /**
     * Convierte EducacionRequest a Educacion con ID generado.
     *
     * @param request DTO de request
     * @param id identificador único
     * @return entidad
     */
    public Educacion toEducacion(EducacionRequest request, String id) {
        if (request == null) {
            return null;
        }

        return Educacion.builder()
                .id(id)
                .institucion(request.getInstitucion())
                .titulo(request.getTitulo())
                .fechaFin(request.getFechaFin())
                .build();
    }

    /**
     * Convierte PortafolioRequest a Portafolio con ID generado.
     *
     * @param request DTO de request
     * @param id identificador único
     * @return entidad
     */
    public Portafolio toPortafolio(PortafolioRequest request, String id) {
        if (request == null) {
            return null;
        }

        return Portafolio.builder()
                .id(id)
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .url(request.getUrl())
                .documentoUrl(request.getDocumentoUrl())
                .build();
    }
}

