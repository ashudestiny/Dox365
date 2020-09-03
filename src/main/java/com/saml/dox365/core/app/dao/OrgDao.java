package com.saml.dox365.core.app.dao;

import com.saml.dox365.core.app.domain.Organization;

public interface OrgDao {

	public void insertOrganization(Organization org);

	public Organization findOrganizationByName(String orgName);
}
