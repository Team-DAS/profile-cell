import Profile, { NivelHabilidad } from '../../models/Profile';

/**
 * Interfaz para los argumentos de las queries
 */
interface GetProfileArgs {
  userId: string;
}

interface GetSkillsByUserIdArgs {
  userId: string;
  nivel?: NivelHabilidad;
}

interface GetExperienceSummaryArgs {
  userId: string;
}

interface GetProfileMetadataArgs {
  userId: string;
}

/**
 * Resolvers para las queries de GraphQL
 */
export const resolvers = {
  Query: {
    /**
     * Query principal: Obtener el perfil completo del usuario
     */
    getProfileDashboard: async (
      _: unknown,
      { userId }: GetProfileArgs
    ) => {
      try {
        const profile = await Profile.findById(userId).lean().exec();
        
        if (!profile) {
          return null;
        }

        return profile;
      } catch (error) {
        console.error('Error en getProfileDashboard:', error);
        throw new Error('Error al obtener el perfil');
      }
    },

    /**
     * Query granular: Obtener habilidades filtradas por nivel
     */
    getSkillsByUserId: async (
      _: unknown,
      { userId, nivel }: GetSkillsByUserIdArgs
    ) => {
      try {
        const profile = await Profile.findById(userId, 'habilidades').lean().exec();

        if (!profile) {
          return [];
        }

        // Filtrar por nivel si se proporciona
        if (nivel) {
          return profile.habilidades.filter(
            (habilidad) => habilidad.nivel === nivel
          );
        }

        return profile.habilidades;
      } catch (error) {
        console.error('Error en getSkillsByUserId:', error);
        throw new Error('Error al obtener las habilidades del usuario');
      }
    },

    /**
     * Query granular: Obtener resumen de experiencia laboral
     */
    getExperienceSummary: async (
      _: unknown,
      { userId }: GetExperienceSummaryArgs
    ) => {
      try {
        const profile = await Profile.findById(userId, 'experienciaLaboral')
          .lean()
          .exec();

        if (!profile) {
          return [];
        }

        return profile.experienciaLaboral;
      } catch (error) {
        console.error('Error en getExperienceSummary:', error);
        throw new Error('Error al obtener la experiencia laboral del usuario');
      }
    },

    /**
     * Query granular: Obtener metadatos del perfil
     */
    getProfileMetadata: async (
      _: unknown,
      { userId }: GetProfileMetadataArgs
    ) => {
      try {
        const profile = await Profile.findById(userId, 'metadata').lean().exec();

        if (!profile) {
          return null;
        }

        return profile.metadata;
      } catch (error) {
        console.error('Error en getProfileMetadata:', error);
        throw new Error('Error al obtener los metadatos del perfil');
      }
    },
  },

  /**
   * Resolver de campo para Profile.id
   * Mapea _id de MongoDB a id de GraphQL
   */
  Profile: {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    id: (parent: any): string => {
      return parent._id;
    },
  },
};
