package com.saml.dox365.core.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.saml.dox365.core.app.domain.License;


/**
 * @author ashish tuteja
 * MongoDb Repository for transaction collection
 */

public interface LicenseRepository extends MongoRepository<License, String>{

}
