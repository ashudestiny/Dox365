package com.saml.dox365.core.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saml.dox365.core.app.dao.OrgDao;
import com.saml.dox365.core.app.domain.Organization;
import com.saml.dox365.core.app.interfaces.OrgDetailsService;
import com.saml.dox365.core.app.repository.OrganizationRepository;

@Service
public class OrgService implements OrgDetailsService{

	@Autowired
	OrgDao orgDao;
	
	@Override
	public void createOrganization(Organization org) {
		orgDao.insertOrganization(org);
	}

	@Override
	public Organization getOrganization(String orgName) {
		return orgDao.findOrganizationByName(orgName);
	}
}
