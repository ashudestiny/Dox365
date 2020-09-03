package com.saml.dox365.core.app.domain;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection = "Organization")
public class Organization {
	
	@Id
	private ObjectId _id;
	
	@Indexed(unique = true)
	private String orgName;
	
	private String createdBy;
	
	private Date createOn;

	@DBRef
	private License license;
	
}
