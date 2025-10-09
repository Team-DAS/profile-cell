package com.udeajobs.profile.profile_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configuración de MongoDB para la aplicación.
 * Habilita repositorios MongoDB y auditoría de entidades.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.udeajobs.profile.profile_service.repository")
@EnableMongoAuditing
public class MongoConfig {

}

