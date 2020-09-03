package com.saml.dox365.core.app.domain;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.annotation.Id;
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
@ComponentScan(basePackages = "com.saml.dox365.core.app.dao.impl")
@Document(collection = CONSTANTS.MONGO_TRANSACTION_TABLE_SUFFIX+"#{@transactionConfigRepositoryCustomImpl.getCollectionName()}")
public class Transaction {

	@Id
	private ObjectId _id;

	private String createdDate;

	private String createBy;
	
	private String mappingId;
	
	private String status;
	
	private String docClass;
	
	private String orgName;
}
