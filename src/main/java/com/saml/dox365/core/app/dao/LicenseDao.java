package com.saml.dox365.core.app.dao;

import com.saml.dox365.core.app.domain.License;

public interface LicenseDao {

	public void insertLicense(License license);

	public License findLicenseByOrgName(String orgName);
}
