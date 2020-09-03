package com.saml.dox365.core.app.controller;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saml.dox365.core.app.domain.Organization;
import com.saml.dox365.core.app.service.OrgService;

@RestController
@RequestMapping("/api/org")
public class OrgController {

	@Autowired
	OrgService orgService;
	
	@PostMapping("/create")
	public void createOrganization(@RequestParam String orgName) {
		
		Organization org = new Organization();
		org.setCreatedBy("ashish");
		org.setCreateOn(Date.valueOf(LocalDate.now()));
		org.setOrgName(orgName);
		
		orgService.createOrganization(org);
		
	}
	
}
