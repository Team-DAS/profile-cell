import dotenv from 'dotenv';

// Cargar variables de entorno
dotenv.config();

/**
 * Configuración simple del servicio
 * MONGODB_URI es requerida como variable de entorno
 */
const config = {
  mongodbUri: process.env.MONGODB_URI || '',
};

/**
 * Valida la configuración
 */
export function validateConfig(): void {
  if (!config.mongodbUri) {
    throw new Error('MONGODB_URI es requerida');
  }
  console.log('✓ Configuración validada');
}

export default config;
