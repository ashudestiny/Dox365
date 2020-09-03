package com.saml.dox365.core.app.repository;

/**
 * @author ashish tuteja
 * MongoDb Repository for Users Collection
 */
import org.springframework.data.mongodb.repository.MongoRepository;

import com.saml.dox365.core.app.domain.Users;

public interface UsersRepository extends MongoRepository<Users, String>{

}
