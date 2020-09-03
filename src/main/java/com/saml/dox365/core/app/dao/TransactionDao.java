package com.saml.dox365.core.app.dao;

import java.util.HashMap;

import org.json.simple.JSONObject;

import com.saml.dox365.core.app.domain.Transaction;


/**
 * 
 * @author ashish tuteja
 * MongoDb DAO for Transaction
 *
 */
public interface TransactionDao {
	
	public void insertTransaction(Transaction transaction);
	
	public void saveUpdateTransactionStatusById(String docId, String status, String orgName);
}