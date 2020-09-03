package com.saml.dox365.core.app.interfaces;

import com.saml.dox365.core.app.domain.Organization;

public interface OrgDetailsService {

	public void createOrganization(Organization org);
	
	public Organization getOrganization(String orgName);
}
