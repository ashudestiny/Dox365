package com.saml.dox365.core.app.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface ConfigRepositoryCustom {
	String getCollectionName();

	void setCollectionName(String collectionName);
}
