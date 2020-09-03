package com.saml.dox365.core.app.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBList;
import com.mongodb.DBRef;
import com.saml.dox365.core.app.dao.OrgDao;
import com.saml.dox365.core.app.domain.License;
import com.saml.dox365.core.app.domain.Organization;
import com.saml.dox365.core.app.repository.LicenseRepository;
import com.saml.dox365.core.app.repository.OrganizationRepository;

@Component
public class OrgDaoImpl implements OrgDao{

	@Autowired
	OrganizationRepository orgRepo; 
	
	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void insertOrganization(Organization org) {
		License license = mongoTemplate.findOne(Query.query(Criteria.where("orgName").is(org.getOrgName())), License.class);
		org.setLicense(license);
		orgRepo.save(org);
	}

	@Override
	public Organization findOrganizationByName(String orgName) {
		Organization org = mongoTemplate.findOne(Query.query(Criteria.where("orgName").is(orgName)), Organization.class);
		return org;
	}

}
