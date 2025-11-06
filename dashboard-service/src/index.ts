import express, { Request, Response } from 'express';
import { ApolloServer } from '@apollo/server';
import { expressMiddleware } from '@apollo/server/express4';
import helmet from 'helmet';
import { validateConfig } from './config';
import { connectDatabase, disconnectDatabase } from './config/database';
import { typeDefs } from './graphql/schema/typeDefs';
import { resolvers } from './graphql/resolvers';
import { metricsHandler } from './metrics';
import { errorHandler, notFoundHandler, formatGraphQLError } from './middleware/errorHandler';

/**
 * Función principal para iniciar el servidor
 */
async function startServer(): Promise<void> {
  try {
    // 1. Validar la configuración
    validateConfig();

    // 2. Conectar a MongoDB (Fail-Fast si falla)
    await connectDatabase();

    // 3. Crear la aplicación Express
    const app = express();

    // 4. Configurar middleware de seguridad (Helmet)
    app.use(helmet({ contentSecurityPolicy: false }));

    // 5. Middleware para parsear JSON
    app.use(express.json());

    // 6. Health Check endpoint
    app.get('/health', (_req: Request, res: Response) => {
      res.status(200).json({ status: 'UP' });
    });

    // 7. Endpoint de métricas de Prometheus
    app.get('/metrics', metricsHandler);

    // 8. Crear y configurar Apollo Server
    const apolloServer = new ApolloServer({
      typeDefs,
      resolvers,
      formatError: formatGraphQLError,
      introspection: true,
    });

    await apolloServer.start();

    // 9. Integrar Apollo Server con Express
    app.use(
      '/graphql',
      expressMiddleware(apolloServer, {
        context: async () => ({}),
      })
    );

    // 10. Middleware de manejo de rutas no encontradas
    app.use(notFoundHandler);

    // 11. Middleware de manejo global de errores
    app.use(errorHandler);

    // 12. Iniciar el servidor HTTP en puerto 8080
    const server = app.listen(8080, () => {
        console.log(`
        ╔════════════════════════════════════════════════╗
        ║   🚀 Dashboard Service iniciado...            ║
        ╚════════════════════════════════════════════════╝
        `);
    });

    // 13. Graceful Shutdown
    const gracefulShutdown = async (signal: string) => {
      console.log(`\n⚠️  Señal ${signal} recibida. Cerrando servidor...`);

      server.close(async () => {
        console.log('🛑 Servidor HTTP cerrado');

        await apolloServer.stop();
        console.log('🛑 Apollo Server detenido');

        await disconnectDatabase();
        console.log('🛑 Base de datos desconectada');

        console.log('👋 Servidor cerrado correctamente');
        process.exit(0);
      });

      // Forzar cierre después de 10 segundos
      setTimeout(() => {
        console.error('⏱️  Forzando cierre después de timeout');
        process.exit(1);
      }, 10000);
    };

    // Listeners para señales de terminación
    process.on('SIGTERM', () => gracefulShutdown('SIGTERM'));
    process.on('SIGINT', () => gracefulShutdown('SIGINT'));

    // Manejar excepciones no capturadas
    process.on('uncaughtException', (error) => {
      console.error('❌ Excepción no capturada:', error);
      gracefulShutdown('uncaughtException');
    });

    process.on('unhandledRejection', (reason) => {
      console.error('❌ Promesa rechazada no manejada:', reason);
      gracefulShutdown('unhandledRejection');
    });
  } catch (error) {
    console.error('❌ Error fatal al iniciar el servidor:', error);
    process.exit(1);
  }
}

// Iniciar el servidor
startServer();
