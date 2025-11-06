import gql from 'graphql-tag';

/**
 * Schema de GraphQL - Type Definitions
 * Mapea la estructura del documento MongoDB a tipos GraphQL
 */
export const typeDefs = gql`
  """
  Nivel de competencia en una habilidad
  """
  enum NivelHabilidad {
    BASICO
    INTERMEDIO
    AVANZADO
    EXPERTO
  }

  """
  Ubicación geográfica del usuario
  """
  type Ubicacion {
    ciudad: String!
    pais: String!
  }

  """
  Información personal del usuario
  """
  type InformacionPersonal {
    nombreCompleto: String!
    email: String!
    tituloProfesional: String!
    resumen: String!
    ubicacion: Ubicacion!
    fotoUrl: String
  }

  """
  Habilidad o competencia del usuario
  """
  type Habilidad {
    id: ID!
    nombre: String!
    nivel: NivelHabilidad!
  }

  """
  Experiencia laboral del usuario
  """
  type ExperienciaLaboral {
    id: ID!
    empresa: String!
    puesto: String!
    fechaInicio: String!
    fechaFin: String
    descripcion: String!
  }

  """
  Formación académica del usuario
  """
  type Educacion {
    id: ID!
    institucion: String!
    titulo: String!
    fechaFin: String!
  }

  """
  Proyecto del portafolio del usuario
  """
  type Portafolio {
    id: ID!
    titulo: String!
    descripcion: String!
    url: String!
    documentoUrl: String
  }

  """
  Metadatos del perfil
  """
  type Metadata {
    perfilCompleto: Boolean!
    fechaCreacion: String!
    ultimaActualizacion: String!
  }

  """
  Perfil completo del usuario (desnormalizado)
  """
  type Profile {
    id: ID!
    informacionPersonal: InformacionPersonal!
    habilidades: [Habilidad!]!
    experienciaLaboral: [ExperienciaLaboral!]!
    educacion: [Educacion!]!
    portafolio: [Portafolio!]!
    metadata: Metadata!
  }

  """
  Queries disponibles
  """
  type Query {
    """
    Obtener el perfil completo de un usuario por userId
    """
    getProfileDashboard(userId: ID!): Profile

    """
    Obtener solo las habilidades de un usuario, opcionalmente filtradas por nivel
    """
    getSkillsByUserId(userId: ID!, nivel: NivelHabilidad): [Habilidad!]

    """
    Obtener un resumen de la experiencia laboral de un usuario
    """
    getExperienceSummary(userId: ID!): [ExperienciaLaboral!]

    """
    Obtener solo los metadatos del perfil de un usuario
    """
    getProfileMetadata(userId: ID!): Metadata
  }
`;
