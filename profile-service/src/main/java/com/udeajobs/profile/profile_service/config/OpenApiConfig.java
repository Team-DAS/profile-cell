package com.udeajobs.profile.profile_service.config;

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
     * Configura la documentación OpenAPI para el servicio de perfiles.
     *
     * Define toda la metadata de la API, incluyendo información general,
     * contacto del equipo, licencia y servidores disponibles.
     *
     * @return instancia de OpenAPI configurada con toda la metadata de la API
     */
    @Bean
    public OpenAPI profileServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("UdeAJobs - Profile Service API")
                        .description("""
                                **API REST para la gestión de perfiles profesionales en UdeAJobs**
                                
                                Este microservicio maneja el ciclo de vida completo de los perfiles de usuario, incluyendo:
                                
                                - 👤 **Información Personal**: Nombre, título profesional, resumen, ubicación y foto
                                - 💼 **Experiencia Laboral**: Historial profesional con empresas, puestos y descripciones
                                - 🎓 **Educación**: Títulos académicos e instituciones educativas
                                - 🛠️ **Habilidades**: Competencias técnicas y niveles de dominio
                                - 📁 **Portafolio**: Proyectos destacados con documentación y URLs
                                
                                ### Características
                                - Arquitectura por capas (Controller → Service → Repository)
                                - Operaciones CRUD completas para cada sección del perfil
                                - Validación exhaustiva de datos con Bean Validation (JSR-303)
                                - Manejo centralizado de excepciones con mensajes descriptivos
                                
                                ### Base de datos
                                - MongoDB para almacenamiento NoSQL flexible
                                - Colección: `profiles`
                                - Estructura embebida para sub-documentos (experiencia, educación, etc.)
                                
                                ### Integración
                                - Event-driven: Recibe eventos de creación de cuenta (Célula 1)
                                - Conecta con file-service para gestión de archivos (fotos, documentos)
                                - Proporciona datos al dashboard-service para visualización
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

