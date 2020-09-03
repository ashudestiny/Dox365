package com.saml.dox365.core.app.dao.impl;

import org.springframework.stereotype.Component;

import com.saml.dox365.core.app.repository.ConfigRepositoryCustom;

@Component
public class TransactionConfigRepositoryCustomImpl implements ConfigRepositoryCustom {
	private static String collectionName = "transactionDefault";

	@Override
	public String getCollectionName() {
		return collectionName;
	}

	@Override
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
}