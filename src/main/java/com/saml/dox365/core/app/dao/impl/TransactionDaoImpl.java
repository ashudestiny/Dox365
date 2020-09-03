package com.saml.dox365.core.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.saml.dox365.core.app.dao.TransactionDao;
import com.saml.dox365.core.app.domain.Transaction;
import com.saml.dox365.core.app.repository.TransactionRepository;
import com.saml.dox365.core.app.util.CONSTANTS;

@Component
public class TransactionDaoImpl implements TransactionDao{


	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	MongoTemplate mongoTemplate; 
	
	@Override
	public void insertTransaction(Transaction transaction) {
		transactionRepository.insert(transaction);
	}


	@Override
	public void saveUpdateTransactionStatusById(String docId, String status, String orgName) {
		
		Transaction transaction = mongoTemplate.findOne(Query.query(Criteria.where("mappingId").is(docId)), Transaction.class, CONSTANTS.MONGO_TRANSACTION_TABLE_SUFFIX+"default1_transaction");
		transaction.setStatus(status);
		transactionRepository.save(transaction);		
	}
}
