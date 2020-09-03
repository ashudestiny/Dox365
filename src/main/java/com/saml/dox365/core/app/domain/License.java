package com.saml.dox365.core.app.domain;


import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection = "License")
public class License {
	
	@Id
	private ObjectId _id;
	
	private String orgName;
	
	private String licenseType;
	
	private boolean licenseValid;
	
	private Date licenseStartDate;
	
	private Date licenseEndDate;
	

}
