package com.udeajobs.profile_cell.file_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Configuración de OpenAPI 3.0 para la documentación Swagger UI.
 *
 * Esta clase configura la información general de la API, incluyendo
 * título, descripción, versión, información de contacto, licencia
 * y servidores disponibles para la documentación interactiva.
 *
 * @author UdeAJobs Team
 * @version 1.0
 * @since 1.0
 */
@Configuration
public class OpenApiConfig {
    /**
     * Configura la documentación OpenAPI para el servicio de archivos.
     *
     * Define toda la metadata de la API, incluyendo información general,
     * contacto del equipo, licencia y servidores disponibles.
     *
     * @return instancia de OpenAPI configurada con toda la metadata de la API
     */
    @Bean
    public OpenAPI fileServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("UdeAJobs - File Service API")
                        .description("""
                                **API REST para la gestión de archivos en UdeAJobs**
                                
                                Este microservicio maneja el almacenamiento y recuperación de archivos en S3/MinIO, incluyendo:
                                
                                - 📤 **Subida de archivos**: Almacenamiento seguro de archivos en buckets categorizados
                                - 📥 **Descarga de archivos**: Recuperación de archivos con URLs firmadas
                                - 🗑️ **Eliminación de archivos**: Borrado seguro de archivos del almacenamiento
                                
                                ### Buckets disponibles
                                - **PROFILES**: Almacenamiento de archivos relacionados con perfiles de usuario (fotos, CVs, etc.)
                                
                                ### Seguridad
                                - Validación de tipos de archivo permitidos
                                - Nombres únicos generados con UUID
                                - Control de acceso por tipo de bucket
                                
                                ### Almacenamiento
                                - Compatible con Amazon S3 y MinIO
                                - Gestión automática de buckets
                                - URLs pre-firmadas para acceso temporal
                                
                                ### Formatos soportados
                                - Imágenes: JPG, PNG, GIF, WebP
                                - Documentos: PDF, DOC, DOCX
                                - Otros formatos según configuración
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("UdeAJobs Development Team")
                                .email("udeajobs674@gmail.com")
                                .url("https://github.com/Team-DAS"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}

