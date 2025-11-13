package com.udeajobs.profile_cell.file_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de OpenAPI 3.0 para la documentación Swagger UI.
 *
 * Esta clase configura la información general de la API, incluyendo
 * título, descripción, versión, información de contacto, licencia
 * y schemas para la documentación interactiva.
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
     * contacto del equipo, licencia y componentes reutilizables.
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
                                
                                - 📤 **Subida de archivos**: Almacenamiento seguro de archivos organizados por carpetas
                                - 📥 **Descarga de archivos**: Recuperación de archivos con URLs de acceso directo
                                - 🗑️ **Eliminación de archivos**: Borrado permanente de archivos del almacenamiento
                                
                                ### Organización de archivos
                                El servicio utiliza un único bucket con carpetas organizadas por tipo:
                                
                                - 🖼️ **profile_images**: Imágenes de perfil de usuario (fotos, avatares)
                                - 📄 **profile_cvs**: Hojas de vida y CVs (documentos PDF, DOCX)
                                
                                ### Características de seguridad
                                - ✅ Validación de tipos de archivo
                                - 🔑 Nombres únicos generados con UUID
                                - 🔒 Prevención de inyección de rutas
                                - 📝 Validación de tamaño de archivos (máx. 50MB)
                                
                                ### Almacenamiento
                                - **Backend**: MinIO (compatible con S3)
                                - **Bucket único**: Configurado vía variable de entorno
                                - **Organización**: Por carpetas según tipo de contenido
                                
                                ### Formato de nombres
                                Los archivos se almacenan con el formato: `{UUID}_{nombre_original}`
                                
                                Ejemplo: `550e8400-e29b-41d4-a716-446655440000_mi_foto.jpg`
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

