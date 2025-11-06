import mongoose from 'mongoose';
import config from '../config';

/**
 * Conecta a la base de datos MongoDB
 * Implementa el patrón Fail-Fast: si la conexión falla, el proceso termina
 */
export async function connectDatabase(): Promise<void> {
  try {
    console.log('🔌 Conectando a MongoDB...');

    await mongoose.connect(config.mongodbUri);

    console.log('✅ Conectado a MongoDB');

    // Listeners para eventos de conexión
    mongoose.connection.on('error', (error) => {
      console.error('❌ Error en la conexión de MongoDB:', error);
    });

    mongoose.connection.on('disconnected', () => {
      console.warn('⚠️  MongoDB desconectado');
    });

    mongoose.connection.on('reconnected', () => {
      console.log('🔄 MongoDB reconectado');
    });
  } catch (error) {
    console.error('❌ Error al conectar a MongoDB:', error);
    process.exit(1);
  }
}

/**
 * Desconecta de la base de datos MongoDB
 * Útil para graceful shutdown
 */
export async function disconnectDatabase(): Promise<void> {
  try {
    await mongoose.connection.close();
    console.log('MongoDB desconectado correctamente');
  } catch (error) {
    console.error('Error al desconectar MongoDB:', error);
  }
}
