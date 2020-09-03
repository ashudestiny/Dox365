package com.saml.dox365.core.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saml.dox365.core.app.domain.Department;
import com.saml.dox365.core.app.service.DepartmentService;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@PostMapping("/create")
	public void createLicense(@RequestParam String departmentName, @RequestParam String departmentAbbrv,
			@RequestParam String orgName) {
		Department department = new Department();
		department.setDepartmentName(departmentName);
		department.setDepartmentAbbrv(departmentAbbrv);

		departmentService.createDepartment(department, orgName);

	}

	@GetMapping("/all")
	public ResponseEntity<List<Department>> getDepartments(@RequestParam String orgName) {
		return ResponseEntity.ok(departmentService.getDeparments(orgName));
	}
}
