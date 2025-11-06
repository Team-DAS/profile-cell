import { Request, Response, NextFunction } from 'express';
import { GraphQLFormattedError } from 'graphql';

/**
 * Clase para errores personalizados de la aplicación
 */
export class AppError extends Error {
  constructor(
    public message: string,
    public statusCode: number = 500,
    public isOperational: boolean = true
  ) {
    super(message);
    Object.setPrototypeOf(this, AppError.prototype);
  }
}

/**
 * Middleware de manejo global de errores para Express
 * Captura excepciones y errores no manejados
 */
export function errorHandler(
  err: Error | AppError,
  _req: Request,
  res: Response,
  _next: NextFunction
): void {
  // Log del error para debugging
  console.error('Error capturado por el middleware:', {
    message: err.message,
    stack: err.stack,
    timestamp: new Date().toISOString(),
  });

  // Determinar el código de estado
  const statusCode = err instanceof AppError ? err.statusCode : 500;

  // Determinar si es un error operacional conocido
  const isOperational = err instanceof AppError ? err.isOperational : false;

  // Respuesta genérica que no expone detalles internos
  res.status(statusCode).json({
    error: {
      message: isOperational ? err.message : 'Internal Server Error',
      status: statusCode,
      ...(process.env.NODE_ENV === 'development' && {
        stack: err.stack,
        details: err.message,
      }),
    },
  });
}

/**
 * Formateador de errores de GraphQL
 * Evita exponer detalles internos de MongoDB u otros sistemas
 * Apollo Server v4 signature: (formattedError, error)
 */
export function formatGraphQLError(
  formattedError: GraphQLFormattedError,
  _error: unknown
): GraphQLFormattedError {
  // Log del error original
  console.error('Error de GraphQL:', {
    message: formattedError.message,
    path: formattedError.path,
    extensions: formattedError.extensions,
    timestamp: new Date().toISOString(),
  });

  // En producción, ocultar detalles internos
  if (process.env.NODE_ENV === 'production') {
    const message = formattedError.message || '';
    
    // No exponer mensajes de MongoDB, stack traces, etc.
    if (
      message.includes('MongoError') ||
      message.includes('mongodb') ||
      message.includes('mongoose')
    ) {
      return {
        message: 'Error interno del servidor',
        extensions: {
          code: 'INTERNAL_SERVER_ERROR',
        },
      };
    }
  }

  return formattedError;
}

/**
 * Middleware para rutas no encontradas
 */
export function notFoundHandler(_req: Request, res: Response): void {
  res.status(404).json({
    error: {
      message: 'Ruta no encontrada',
      status: 404,
    },
  });
}
