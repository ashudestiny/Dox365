package com.saml.dox365.core.app.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(collection = "Users")
public class Users {

	@Id
	private ObjectId _id;
	
	private String username;
	
	private String password;
	
	private HashMap<String,ArrayList<String>> roles = new HashMap<String,ArrayList<String>>();

}
