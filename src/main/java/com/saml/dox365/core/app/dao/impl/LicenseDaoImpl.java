package com.saml.dox365.core.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.saml.dox365.core.app.dao.LicenseDao;
import com.saml.dox365.core.app.domain.License;
import com.saml.dox365.core.app.repository.LicenseRepository;

@Component
public class LicenseDaoImpl implements LicenseDao{

	@Autowired
	LicenseRepository licenseRepo; 
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void insertLicense(License license) {
		licenseRepo.insert(license);
	}

	@Override
	public License findLicenseByOrgName(String orgName) {
		License license = mongoTemplate.findOne(Query.query(Criteria.where("orgName").is(orgName)), License.class);
		return license;
	}

}
