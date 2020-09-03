package com.saml.dox365.core.app.controller;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saml.dox365.core.app.domain.License;
import com.saml.dox365.core.app.service.LicenseService;

@RestController
@RequestMapping("/api/license")
public class LicenseController {

	@Autowired
	LicenseService licenseService;
	
	@PostMapping("/create")
	public void createLicense(@RequestParam String orgName, @RequestParam int noOfDays) {
		License license = new License();
		license.setLicenseStartDate(java.sql.Date.valueOf(LocalDate.now()));
		license.setLicenseEndDate(java.sql.Date.valueOf(LocalDate.now().plusYears(noOfDays)));
		license.setLicenseValid(true);
		license.setOrgName(orgName);
		
		licenseService.createLicense(license);
	}
	
	@GetMapping("/")
	public ResponseEntity<License> getLicense(@RequestParam String orgName) {
		return ResponseEntity.ok(licenseService.getLicense(orgName));
	}
	
}
