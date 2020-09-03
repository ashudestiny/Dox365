package com.saml.dox365.core.app.dao.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.saml.dox365.core.app.dao.TemplateDao;
import com.saml.dox365.core.app.domain.Template;
import com.saml.dox365.core.app.repository.TemplateRepository;

/**
 * 
 * @author ashish tuteja
 * Template Dao Implementation of different mongo db operations for templates
 */
@Component
public class TemplateDaoImpl implements TemplateDao {
	
	@Autowired
	TemplateRepository templateRepository;
	
	@Autowired
	MongoTemplate mongoTemplate; 
	
	/**
	 * Insert the template in Document in mongoDB
	 */
	@Override
	public void insertTemplate(Template template) {
		templateRepository.insert(template);
	}
	
	/**
	 * Update existing document else create new 
	 */
	@Override
	public void saveInsertTemplate(Template template) {
		templateRepository.save(template);
	}
	
	/*
	 * Update specific template based in template name
	 */
	@Override
	public void saveUpdateTemplateByName(String templateName, JSONObject newJsonValue) {
		
		Template template = mongoTemplate.findOne(Query.query(Criteria.where("templateName").is(templateName)), Template.class);
		template.setTemplateJson(newJsonValue);
		templateRepository.save(template);
	}
	
	/**
	 * Delete template from db based on template name
	 */
	@Override
	public void deleteTemplateByName(String templateName) {
		Template template = mongoTemplate.findOne(Query.query(Criteria.where("templateName").is(templateName)), Template.class);
		templateRepository.delete(template);
	}
	
	
	@Override
	public Template findOneTemplateByName(String templateName) {
		Template template = mongoTemplate.findOne(Query.query(Criteria.where("templateName").is(templateName)), Template.class);
		return template;
		
	}
	
	/**
	 * Fetch all templates from db
	 */
	@Override
	public List<Template> findAllWithSortTemplate() {
		List<Template> templateList = templateRepository.findAll();
		return templateList;
	}
	
	@Override
	public void existsTemplate() {
		// TODO Auto-generated method stub
		
	}
}
