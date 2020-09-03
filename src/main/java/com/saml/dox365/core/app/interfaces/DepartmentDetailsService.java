package com.saml.dox365.core.app.interfaces;

import java.util.List;

import com.saml.dox365.core.app.domain.Department;

public interface DepartmentDetailsService {

	public void createDepartment(Department department, String orgName);
	
	public List<Department> getDeparments(String orgName);
}
