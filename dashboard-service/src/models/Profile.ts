import mongoose, { Schema, Document } from 'mongoose';

/**
 * Enumeración para los niveles de habilidad
 */
export enum NivelHabilidad {
  BASICO = 'BASICO',
  INTERMEDIO = 'INTERMEDIO',
  AVANZADO = 'AVANZADO',
  EXPERTO = 'EXPERTO',
}

/**
 * Interfaces para sub-documentos
 */
export interface IUbicacion {
  ciudad: string;
  pais: string;
}

export interface IInformacionPersonal {
  nombreCompleto: string;
  email: string;
  tituloProfesional: string;
  resumen: string;
  ubicacion: IUbicacion;
  fotoUrl?: string;
}

export interface IHabilidad {
  id: string;
  nombre: string;
  nivel: NivelHabilidad;
}

export interface IExperienciaLaboral {
  id: string;
  empresa: string;
  puesto: string;
  fechaInicio: Date;
  fechaFin?: Date | null;
  descripcion: string;
}

export interface IEducacion {
  id: string;
  institucion: string;
  titulo: string;
  fechaFin: Date;
}

export interface IPortafolio {
  id: string;
  titulo: string;
  descripcion: string;
  url: string;
  documentoUrl?: string | null;
}

export interface IMetadata {
  perfilCompleto: boolean;
  fechaCreacion: Date;
  ultimaActualizacion: Date;
}

/**
 * Interfaz principal para el documento de perfil
 * El _id es el userId de la Célula 1
 */
export interface IProfile extends Document {
  _id: string; // userId
  informacionPersonal: IInformacionPersonal;
  habilidades: IHabilidad[];
  experienciaLaboral: IExperienciaLaboral[];
  educacion: IEducacion[];
  portafolio: IPortafolio[];
  metadata: IMetadata;
}

/**
 * Schema de Mongoose - Altamente desnormalizado
 */
const ProfileSchema = new Schema<IProfile>(
  {
    _id: {
      type: String,
    },
    informacionPersonal: {
      nombreCompleto: { type: String },
      email: { type: String },
      tituloProfesional: { type: String },
      resumen: { type: String },
      ubicacion: {
        ciudad: { type: String },
        pais: { type: String },
      },
      fotoUrl: { type: String },
    },
    habilidades: [
      {
        id: { type: String },
        nombre: { type: String },
        nivel: {
          type: String,
          enum: Object.values(NivelHabilidad),
        },
      },
    ],
    experienciaLaboral: [
      {
        id: { type: String },
        empresa: { type: String },
        puesto: { type: String },
        fechaInicio: { type: Date },
        fechaFin: { type: Date },
        descripcion: { type: String },
      },
    ],
    educacion: [
      {
        id: { type: String },
        institucion: { type: String },
        titulo: { type: String },
        fechaFin: { type: Date },
      },
    ],
    portafolio: [
      {
        id: { type: String },
        titulo: { type: String },
        descripcion: { type: String },
        url: { type: String },
        documentoUrl: { type: String },
      },
    ],
    metadata: {
      perfilCompleto: { type: Boolean },
      fechaCreacion: { type: Date },
      ultimaActualizacion: { type: Date },
    },
  },
  {
    // No usar timestamps automáticos ya que los manejamos en metadata
    timestamps: false,
    // Nombre de la colección
    collection: 'profiles',
  }
);

// Índices para optimizar consultas
ProfileSchema.index({ 'informacionPersonal.email': 1 });
ProfileSchema.index({ 'metadata.perfilCompleto': 1 });
ProfileSchema.index({ 'habilidades.nivel': 1 });

/**
 * Modelo de Mongoose
 */
const Profile = mongoose.model<IProfile>('Profile', ProfileSchema);

export default Profile;
