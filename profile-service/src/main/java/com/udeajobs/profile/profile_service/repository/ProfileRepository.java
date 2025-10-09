package com.udeajobs.profile.profile_service.repository;

import com.udeajobs.profile.profile_service.entity.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para operaciones CRUD sobre perfiles de usuario en MongoDB.
 *
 * @author UdeAJobs Team
 * @version 1.0
 */
@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

}

