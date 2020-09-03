package com.saml.dox365.core.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saml.dox365.core.app.dao.DepartmentDao;
import com.saml.dox365.core.app.domain.Department;
import com.saml.dox365.core.app.interfaces.DepartmentDetailsService;

@Service
public class DepartmentService implements DepartmentDetailsService{

	@Autowired
	DepartmentDao departmentDao;

	@Override
	public void createDepartment(Department department, String orgName) {
		departmentDao.insertDepartment(department, orgName);
	}

	@Override
	public List<Department> getDeparments(String orgName) {
		return departmentDao.findDepartmentsByOrgName(orgName);
	}
	
	
}
