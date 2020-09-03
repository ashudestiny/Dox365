package com.saml.dox365.core.app.dao;

import java.util.List;

import org.json.simple.JSONObject;

import com.saml.dox365.core.app.domain.Template;


/**
 * 
 * @author ashish tuteja
 * MongoDb DAO for Templates
 *
 */
public interface TemplateDao {
	
	public void insertTemplate(Template template);
	
	public void saveInsertTemplate(Template template);
	
	public void saveUpdateTemplateByName(String templateName, JSONObject newJsonValue);
		
	public void deleteTemplateByName(String templateName);
	
	public Template findOneTemplateByName(String templateName);
	
	public void existsTemplate();
	
	public List<Template> findAllWithSortTemplate();

	
}
