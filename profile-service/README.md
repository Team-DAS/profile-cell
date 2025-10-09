# Profile Service - UdeAJobs

Microservicio de gestión de perfiles profesionales para la plataforma UdeAJobs.

## 📋 Descripción

Este servicio gestiona toda la información del perfil profesional de los usuarios, incluyendo:
- Información personal
- Habilidades técnicas y blandas
- Experiencia laboral
- Educación
- Portafolio de proyectos

## 🏗️ Arquitectura

El proyecto sigue una **arquitectura por capas**:

```
src/main/java/com/udeajobs/profile/profile_service/
├── controller/          # Controladores REST
├── service/            # Lógica de negocio
├── repository/         # Acceso a datos (MongoDB)
├── entity/             # Entidades del dominio
├── dto/                # Data Transfer Objects
│   ├── request/       # DTOs de entrada
│   └── response/      # DTOs de salida
├── mapper/             # Conversión entre DTOs y entidades
├── exception/          # Excepciones personalizadas
├── config/             # Configuración
└── enums/              # Enumeraciones
```

## 🚀 Tecnologías

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring Data MongoDB** - Base de datos NoSQL
- **Spring Validation** - Validación de DTOs
- **Lombok** - Reducción de código boilerplate
- **SLF4J** - Logging
- **Gradle** - Gestión de dependencias

## 📊 Modelo de Datos

### Entidades Principales

- **Profile**: Entidad raíz que contiene toda la información del perfil
- **InformacionPersonal**: Datos personales básicos
- **Habilidad**: Habilidades con nivel de dominio
- **ExperienciaLaboral**: Historial laboral
- **Educacion**: Formación académica
- **Portafolio**: Proyectos destacados
- **Metadata**: Información de auditoría

### Enumeraciones

- **NivelHabilidad**: BASICO, INTERMEDIO, AVANZADO, EXPERTO

## 🔌 API Endpoints

### Base URL: `/api/v1/profiles`

#### Perfil Completo
- `GET /{userId}` - Obtener perfil completo

#### Información Personal
- `PUT /{userId}/personal-info` - Actualizar información personal

#### Experiencia Laboral
- `POST /{userId}/experience` - Añadir experiencia
- `PUT /{userId}/experience/{experienceId}` - Actualizar experiencia
- `DELETE /{userId}/experience/{experienceId}` - Eliminar experiencia

#### Habilidades
- `POST /{userId}/skills` - Añadir habilidad
- `DELETE /{userId}/skills/{skillId}` - Eliminar habilidad

#### Educación
- `POST /{userId}/education` - Añadir educación
- `PUT /{userId}/education/{educationId}` - Actualizar educación
- `DELETE /{userId}/education/{educationId}` - Eliminar educación

#### Portafolio
- `POST /{userId}/portfolio` - Añadir proyecto
- `PUT /{userId}/portfolio/{portfolioId}` - Actualizar proyecto
- `DELETE /{userId}/portfolio/{portfolioId}` - Eliminar proyecto

## ✅ Validaciones

Todos los DTOs de request incluyen validaciones robustas:

- **@NotBlank**: Campos obligatorios
- **@Size**: Longitud mínima y máxima
- **@NotNull**: Valores no nulos
- **@PastOrPresent**: Fechas válidas
- **@URL**: URLs bien formadas
- **@Valid**: Validación en cascada

## 🔧 Configuración

### application.properties

```properties
# MongoDB
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=udeajobs_profiles

# Servidor
server.port=8081

# Logging
logging.level.com.udeajobs.profile.profile_service=DEBUG
```

## 🛡️ Manejo de Errores

El servicio implementa un **manejo global de excepciones** mediante `@ControllerAdvice`:

### Excepciones Personalizadas
- `ProfileNotFoundException` (404) - Perfil no encontrado
- `ResourceNotFoundException` (404) - Recurso no encontrado
- `InvalidDataException` (400) - Datos inválidos

### Respuestas de Error Estructuradas
```json
{
  "status": 404,
  "message": "Perfil no encontrado para el usuario con ID: xyz",
  "timestamp": "2025-10-09T10:30:00",
  "path": "/api/v1/profiles/xyz"
}
```

### Errores de Validación
```json
{
  "status": 400,
  "message": "Error de validación en los datos proporcionados",
  "timestamp": "2025-10-09T10:30:00",
  "path": "/api/v1/profiles/xyz/experience",
  "errors": [
    {
      "field": "empresa",
      "message": "El nombre de la empresa es obligatorio"
    }
  ]
}
```

## 📝 Logging

El servicio implementa logging en múltiples niveles:

- **INFO**: Operaciones principales (CRUD)
- **DEBUG**: Detalles de ejecución
- **WARN**: Recursos no encontrados
- **ERROR**: Errores críticos

Ejemplo:
```
2025-10-09 10:30:00 - INFO  - Añadiendo experiencia laboral para el usuario: user-123
2025-10-09 10:30:01 - DEBUG - Metadatos actualizados para el perfil. PerfilCompleto: true
```

## 🎯 Características Especiales

### Completitud del Perfil
El servicio calcula automáticamente si un perfil está completo:
- Tiene información personal completa
- Al menos 1 habilidad registrada
- Al menos 1 experiencia laboral

### Auditoría Automática
- `fechaCreacion`: Se establece al crear el perfil
- `ultimaActualizacion`: Se actualiza en cada modificación
- `perfilCompleto`: Se recalcula automáticamente

### IDs Únicos para Sub-recursos
Cada elemento de las listas (experiencias, habilidades, etc.) tiene un UUID único para operaciones CRUD individuales.

## 🚀 Ejecución

### Prerequisitos
- Java 21
- MongoDB en localhost:27017
- Gradle

### Compilar
```bash
./gradlew clean build
```

### Ejecutar
```bash
./gradlew bootRun
```

### Ejecutar tests
```bash
./gradlew test
```

## 📚 Documentación JavaDoc

Todas las clases, métodos y campos están documentados con JavaDoc estándar:

```java
/**
 * Añade una nueva experiencia laboral al perfil.
 * 
 * @param userId identificador del usuario
 * @param request datos de la experiencia
 * @return experiencia creada con su ID
 * @throws ProfileNotFoundException si no existe el perfil
 */
```

## 🔄 Integración Futura

El servicio está preparado para:
- Consumir eventos de RabbitMQ (configuración incluida)
- Integrarse con `file-service` para gestión de archivos
- Comunicarse con `dashboard-service` para métricas

## 👥 Equipo

UdeAJobs Development Team

## 📄 Licencia

Proyecto académico - Universidad de Antioquia

