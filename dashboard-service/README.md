# Dashboard Service 📊

Microservicio de solo lectura (Célula 2) que expone un API GraphQL sobre una base de datos MongoDB desnormalizada para dashboards de perfil de usuario.

## 🏗️ Arquitectura

- **Framework**: Express.js + TypeScript
- **GraphQL**: Apollo Server v4
- **Base de Datos**: MongoDB (Mongoose ODM)
- **Métricas**: Prometheus (prom-client)
- **Seguridad**: Helmet + CORS
- **Containerización**: Docker (multi-stage build con Node Alpine)

## 📋 Características

### ✅ Queries GraphQL

#### Query Principal
- `getProfileDashboard(userId: ID!): Profile` - Obtiene el perfil completo del usuario

#### Queries Granulares
- `getSkillsByUserId(userId: ID!, nivel: NivelHabilidad): [Habilidad!]` - Obtiene habilidades con filtrado opcional por nivel
- `getExperienceSummary(userId: ID!): [ExperienciaLaboral!]` - Obtiene experiencia laboral
- `getProfileMetadata(userId: ID!): Metadata` - Obtiene metadatos del perfil

### 📊 Métricas de Prometheus

- **Métricas del sistema**: CPU, memoria, event loop (automáticas)
- **Métricas personalizadas**:
  - `dashboard_query_total` - Contador de queries por estado (success/error/not_found)
  - `dashboard_query_duration_seconds` - Histograma de duración de queries

### 🔐 Seguridad

- Helmet para headers HTTP seguros
- CORS configurado por variable de entorno
- Manejo centralizado de errores (no expone detalles internos en producción)
- Usuario no-root en Docker

### 🏥 Health & DevOps

- **Endpoint de Health Check**: `GET /status`
- **Endpoint de Métricas**: `GET /metrics`
- **Fail-Fast**: El servicio no arranca si no puede conectar a MongoDB
- **Graceful Shutdown**: Cierre limpio en señales SIGTERM/SIGINT
- **Health Check en Docker**: Monitoreo automático del contenedor

## 🚀 Inicio Rápido

### Prerrequisitos

- Node.js >= 18
- MongoDB 
- npm >= 9

### Instalación Local

```bash
# 1. Instalar dependencias
npm install

# 2. Configurar variables de entorno
cp .env.example .env
# Editar .env con tus valores

# 3. Ejecutar en modo desarrollo
npm run dev

# 4. Compilar para producción
npm run build

# 5. Ejecutar en producción
npm start
```

### Docker

```bash
# Construir imagen
docker build -t dashboard-service:latest .

# Ejecutar contenedor
docker run -d \
  --name dashboard-service \
  -p 4000:4000 \
  -e MONGODB_URI=mongodb://host.docker.internal:27017/profiles-dashboard \
  -e ALLOWED_ORIGINS=http://localhost:3000 \
  dashboard-service:latest
```

## ⚙️ Variables de Entorno

| Variable | Descripción | Ejemplo | Requerida |
|----------|-------------|---------|-----------|
| `NODE_ENV` | Entorno de ejecución | `production` / `development` | No (default: `development`) |
| `PORT` | Puerto del servidor | `4000` | No (default: `4000`) |
| `MONGODB_URI` | URI de conexión a MongoDB | `mongodb://localhost:27017/profiles-dashboard` | **Sí** |
| `ALLOWED_ORIGINS` | Orígenes CORS permitidos (separados por coma) | `http://localhost:3000,http://localhost:5173` | No (default: `http://localhost:3000`) |
| `GRAPHQL_PATH` | Ruta del endpoint GraphQL | `/graphql` | No (default: `/graphql`) |
| `METRICS_PATH` | Ruta del endpoint de métricas | `/metrics` | No (default: `/metrics`) |

## 📚 Estructura del Proyecto

```
dashboard-service/
├── src/
│   ├── config/           # Configuración y variables de entorno
│   │   ├── index.ts
│   │   └── database.ts
│   ├── models/           # Modelos de Mongoose
│   │   └── Profile.ts
│   ├── graphql/          # Schema y resolvers de GraphQL
│   │   ├── schema/
│   │   │   └── typeDefs.ts
│   │   └── resolvers/
│   │       └── index.ts
│   ├── middleware/       # Middleware de Express
│   │   └── errorHandler.ts
│   ├── metrics/          # Configuración de Prometheus
│   │   └── index.ts
│   └── index.ts          # Entry point
├── Dockerfile
├── .dockerignore
├── package.json
├── tsconfig.json
└── .env.example
```

## 🗄️ Modelo de Datos

El esquema MongoDB está **altamente desnormalizado** con sub-documentos embebidos:

```typescript
{
  "_id": "userId",
  "informacionPersonal": {
    "nombreCompleto": "String",
    "email": "String",
    "tituloProfesional": "String",
    "resumen": "String",
    "ubicacion": { "ciudad": "String", "pais": "String" },
    "fotoUrl": "String"
  },
  "habilidades": [
    { "id": "UUID", "nombre": "String", "nivel": "BASICO|INTERMEDIO|AVANZADO|EXPERTO" }
  ],
  "experienciaLaboral": [...],
  "educacion": [...],
  "portafolio": [...],
  "metadata": {
    "perfilCompleto": "Boolean",
    "fechaCreacion": "Date",
    "ultimaActualizacion": "Date"
  }
}
```

## 🧪 Ejemplo de Query GraphQL

```graphql
query GetProfileDashboard {
  getProfileDashboard(userId: "123e4567-e89b-12d3-a456-426614174000") {
    id
    informacionPersonal {
      nombreCompleto
      email
      tituloProfesional
      ubicacion {
        ciudad
        pais
      }
    }
    habilidades {
      nombre
      nivel
    }
    metadata {
      perfilCompleto
      ultimaActualizacion
    }
  }
}

query GetSkillsByLevel {
  getSkillsByUserId(
    userId: "123e4567-e89b-12d3-a456-426614174000"
    nivel: AVANZADO
  ) {
    nombre
    nivel
  }
}
```

## 📊 Endpoints

| Endpoint | Método | Descripción |
|----------|--------|-------------|
| `/graphql` | POST | GraphQL API |
| `/status` | GET | Health check (Liveness/Readiness) |
| `/metrics` | GET | Métricas de Prometheus |

## 🛡️ Buenas Prácticas Implementadas

### Express & Node.js
- ✅ Arquitectura por capas (config, models, graphql, middleware, metrics)
- ✅ Validación de configuración con Fail-Fast
- ✅ Manejo centralizado de errores
- ✅ Graceful shutdown
- ✅ Helmet para seguridad HTTP
- ✅ CORS configurado
- ✅ Variables de entorno validadas

### DevOps
- ✅ Dockerfile multi-stage para optimización
- ✅ Imagen base Alpine (lightweight)
- ✅ Usuario no-root en contenedor
- ✅ Health check integrado
- ✅ dumb-init para manejo de señales
- ✅ Métricas de Prometheus expuestas
- ✅ Logs estructurados con emojis para facilitar debugging

### MongoDB
- ✅ Conexión con mongoose
- ✅ Fail-Fast en conexión fallida
- ✅ Índices optimizados
- ✅ Esquema desnormalizado para lectura rápida

### GraphQL
- ✅ Schema type-safe con TypeScript
- ✅ Formateo de errores (no expone detalles internos)
- ✅ Introspection solo en desarrollo
- ✅ Métricas personalizadas por query

## 🔧 Scripts NPM

```bash
npm run build         # Compilar TypeScript
npm start            # Ejecutar en producción
npm run dev          # Ejecutar en desarrollo con ts-node
npm run dev:watch    # Ejecutar con auto-reload (nodemon)
npm run lint         # Linter con ESLint
npm run format       # Formatear código con Prettier
```

## 📝 Logs y Debugging

El servicio utiliza logs estructurados con emojis:

- 🔍 Validación de configuración
- 🔌 Conexión a MongoDB
- ✅ Operaciones exitosas
- ❌ Errores
- ⚠️ Advertencias
- 🛑 Shutdown

## 🚦 Integración con Kubernetes

El servicio está diseñado para Kubernetes:

```yaml
# Ejemplo de probes
livenessProbe:
  httpGet:
    path: /status
    port: 4000
  initialDelaySeconds: 10
  periodSeconds: 30

readinessProbe:
  httpGet:
    path: /status
    port: 4000
  initialDelaySeconds: 5
  periodSeconds: 10
```

## 🤝 Contribución

Este servicio es parte de la arquitectura de microservicios de UdeAJobs.

## 📄 Licencia

MIT

---

**Team-DAS** © 2024
