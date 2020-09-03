package com.saml.dox365.core.app.domain;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.saml.dox365.core.app.util.CONSTANTS;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author ashish tuteja
 * Transaction with all the details to be saved in mongo Db
 */

@Getter
@Setter
@Document(collection = "Department")
public class Department {

	@Id
	private ObjectId _id;

	private String createdDate;

	private String createBy;
	
	private String departmentName;
	
	private String departmentAbbrv;
	
	@DBRef
	private Organization org;
}
