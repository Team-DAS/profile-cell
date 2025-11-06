import { Request, Response } from 'express';
import { register, collectDefaultMetrics } from 'prom-client';

/**
 * Configuración de métricas de Prometheus
 */

// Recolectar métricas por defecto del sistema (CPU, memoria, event loop, etc.)
collectDefaultMetrics({
  prefix: 'profile_service_',
  labels: { service: 'profile' },
});

/**
 * Handler para el endpoint /metrics
 * Expone las métricas en formato Prometheus
 */
export async function metricsHandler(_req: Request, res: Response): Promise<void> {
  try {
    res.set('Content-Type', register.contentType);
    const metrics = await register.metrics();
    res.end(metrics);
  } catch (error) {
    res.status(500).json({ error: 'Error al obtener las métricas' });
  }
}

/**
 * Obtiene el registro de métricas
 */
export function getMetricsRegistry() {
  return register;
}
