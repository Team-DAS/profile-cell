# ======================================
# Stage 1: Build
# ======================================
FROM node:20-alpine AS builder


# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de dependencias
COPY package*.json ./
COPY tsconfig.json ./

# Instalar dependencias (incluidas dev para compilar TypeScript)
RUN npm ci --only=production=false

# Copiar código fuente
COPY src ./src

# Compilar TypeScript a JavaScript
RUN npm run build

# ======================================
# Stage 2: Production
# ======================================
FROM node:20-alpine

# Instalar dumb-init para manejo correcto de señales
RUN apk add --no-cache dumb-init

# Crear usuario no-root por seguridad
RUN addgroup -g 1001 -S nodejs && \
    adduser -S nodejs -u 1001

# Establecer directorio de trabajo
WORKDIR /app

# Copiar archivos de dependencias
COPY package*.json ./

# Instalar solo dependencias de producción
RUN npm ci --only=production && \
    npm cache clean --force

# Copiar el código compilado desde el stage de build
COPY --from=builder /app/dist ./dist

# Cambiar ownership al usuario no-root
RUN chown -R nodejs:nodejs /app

# Cambiar al usuario no-root
USER nodejs

# Usar dumb-init para manejo correcto de señales SIGTERM
ENTRYPOINT ["dumb-init", "--"]

# Comando para ejecutar la aplicación
CMD ["node", "dist/index.js"]
