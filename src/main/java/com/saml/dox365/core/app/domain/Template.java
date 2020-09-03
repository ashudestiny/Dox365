package com.saml.dox365.core.app.domain;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ashish tuteja
 * Template with all the details to be saved in mongo Db
 */
@Getter
@Setter
@Document(collection = "Templates")
public class Template {

	@Id
	private ObjectId _id;

	private int departmentId;
	
	@Indexed(unique = true)
	private String templateName;
	
	private String orgName;
	
	private JSONObject templateJson;
}
