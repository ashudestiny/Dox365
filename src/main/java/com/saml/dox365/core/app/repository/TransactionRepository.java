package com.saml.dox365.core.app.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.saml.dox365.core.app.domain.Transaction;


/**
 * @author ashish tuteja
 * MongoDb Repository for transaction collection
 */

public interface TransactionRepository extends MongoRepository<Transaction, String>{

}
