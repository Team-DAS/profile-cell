const fastify = require('fastify')({ logger: true });

// Importar las librerías necesarias
const httpProxy = require('@fastify/http-proxy');
const metrics = require('fastify-metrics'); 

fastify.register(metrics, { 
  endpoint: '/metrics',
});

// --- 1. Configuración (Leída de variables de entorno) ---
const PROFILE_SERVICE_URL = process.env.PROFILE_SERVICE_URL;
const FILES_SERVICE_URL = process.env.FILES_SERVICE_URL;

// --- 3. Registro de Rutas (El Proxy) ---

fastify.register(httpProxy, {
  upstream: PROFILE_SERVICE_URL,
  prefix: 'profile-cell/api/v1/profile', 
  rewritePrefix: '/api/v1/profile', 
});


fastify.register(httpProxy, {
  upstream: FILES_SERVICE_URL,
  prefix: 'profile-cell/api/v1/files', 
  rewritePrefix: '/api/v1/files', 
});

// --- 4. Iniciar el Servidor ---
const start = async () => {
  try {
    // Escuchamos en 0.0.0.0 (para Docker) y en el puerto 8080
    await fastify.listen({ port: 8080, host: '0.0.0.0' });
  } catch (err) {
    fastify.log.error(err);
    process.exit(1);
  }
};
start();