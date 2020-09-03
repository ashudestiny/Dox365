package com.saml.dox365.core.app.dao;

import java.util.List;

import com.saml.dox365.core.app.domain.Department;

public interface DepartmentDao {

	public void insertDepartment(Department department, String orgName);

	public List<Department> findDepartmentsByOrgName(String orgName);
}
