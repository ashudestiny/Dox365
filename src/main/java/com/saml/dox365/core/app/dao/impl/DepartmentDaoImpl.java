package com.saml.dox365.core.app.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.saml.dox365.core.app.dao.DepartmentDao;
import com.saml.dox365.core.app.domain.Department;
import com.saml.dox365.core.app.domain.License;
import com.saml.dox365.core.app.domain.Organization;
import com.saml.dox365.core.app.repository.DepartmentRepository;

@Component
public class DepartmentDaoImpl implements DepartmentDao{

	@Autowired
	DepartmentRepository departmentRepo; 
	
	@Autowired
	MongoTemplate mongoTemplate;


	@Override
	public void insertDepartment(Department department, String orgName) {
		Organization org = mongoTemplate.findOne(Query.query(Criteria.where("orgName").is(orgName)), Organization.class);
		department.setOrg(org);
		departmentRepo.insert(department);
	}

	@Override
	public List<Department> findDepartmentsByOrgName(String orgName) {
		List<Department> departments = mongoTemplate.find(Query.query(Criteria.where("orgName").is(orgName)), Department.class);
		return departments;
	}

}
