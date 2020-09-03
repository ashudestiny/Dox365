package com.saml.dox365.core.app.repository;

/**
 * @author ashish tuteja
 * MongoDb Repository for Template Collection
 */
import org.springframework.data.mongodb.repository.MongoRepository;

import com.saml.dox365.core.app.domain.Template;

public interface TemplateRepository extends MongoRepository<Template, String>{

}
