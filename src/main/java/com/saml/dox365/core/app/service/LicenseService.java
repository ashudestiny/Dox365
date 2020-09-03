package com.saml.dox365.core.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saml.dox365.core.app.dao.LicenseDao;
import com.saml.dox365.core.app.dao.OrgDao;
import com.saml.dox365.core.app.domain.License;
import com.saml.dox365.core.app.domain.Organization;
import com.saml.dox365.core.app.interfaces.LicenseDetailsService;
import com.saml.dox365.core.app.interfaces.OrgDetailsService;
import com.saml.dox365.core.app.repository.OrganizationRepository;

@Service
public class LicenseService implements LicenseDetailsService{

	@Autowired
	LicenseDao licenseDao;

	@Override
	public void createLicense(License license) {
		licenseDao.insertLicense(license);
	}

	@Override
	public License getLicense(String orgName) {
		return licenseDao.findLicenseByOrgName(orgName);
	}
	
	
}
