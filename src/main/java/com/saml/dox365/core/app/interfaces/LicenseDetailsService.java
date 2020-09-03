package com.saml.dox365.core.app.interfaces;

import com.saml.dox365.core.app.domain.License;

public interface LicenseDetailsService {

	public void createLicense(License license);
	
	public License getLicense(String orgName);
}
