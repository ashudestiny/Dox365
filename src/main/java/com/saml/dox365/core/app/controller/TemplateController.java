package com.saml.dox365.core.app.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.saml.dox365.core.app.config.FlowProperties;
import com.saml.dox365.core.app.dao.TemplateDao;
import com.saml.dox365.core.app.domain.Template;
import com.saml.dox365.core.app.exceptions.DoxTemplateException;
import com.saml.dox365.core.app.repository.TemplateRepository;
import com.saml.dox365.core.app.util.CONSTANTS;
import com.saml.dox365.core.app.util.CommonUtil;
import com.saml.dox365.core.app.util.DBUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author ashish tuteja API's for template action
 */
@RestController
@Api(value = "Template Api's", description = "Operations pertaining to metadata template")
@RequestMapping("/api/template")
public class TemplateController {
	Logger log = LoggerFactory.getLogger(TemplateController.class);

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	CommonUtil util;

	@Autowired
	FlowProperties properties;

	@Autowired
	TemplateDao templateDao;

	@Autowired
	DBUtil dbUtil;
	
	@ApiOperation(value = "Submit new template")
	@PostMapping(name = "/")
	@ResponseStatus
	public ResponseEntity<String> uploadTemplate(@RequestParam String templateName, @RequestParam String orgName,
			@RequestParam String fieldJson) throws Exception {

		log.info("Storing template");
		dbUtil.insertTemplate(templateName, fieldJson, orgName);
		return new ResponseEntity<String>("Template successfully created !", HttpStatus.CREATED);
		
		/*
		 * try {
		 * 
		 * } catch (DuplicateKeyException e) { return new
		 * ResponseEntity<String>("Template with name \"" + templateName +
		 * "\" already exists!", HttpStatus.BAD_REQUEST); } catch (Exception e) { throw
		 * new DoxTemplateException("Error while saving template, Template Name : " +
		 * templateName, e); }
		 */
		

	}

	//@PreAuthorize("hasAuthority('READ_PRIVILEGE')")
	@ApiOperation(value = "Fetch all templates")
	@GetMapping("/all")
	@ResponseStatus
	public ResponseEntity<List<Template>> getAllTemplates() throws DoxTemplateException {

		List<Template> templateList;
		log.info("Fetching all templates");
		try {
			templateList = dbUtil.findAllTemplate();
		} catch (Exception e) {
			throw new DoxTemplateException("Error while fetching templates!", e);
		}
		return new ResponseEntity<List<Template>>(templateList, HttpStatus.OK);
	}

	@ApiOperation(value = "Delete template using template name")
	@DeleteMapping("/{templateName}")
	@ResponseStatus
	public ResponseEntity<String> deleteTemplateByName(@PathVariable(name = "templateName") String templateName)
			throws DoxTemplateException {

		log.info("Fetching all templates");
		try {
			dbUtil.deleteTemplate(templateName);
		} catch (Exception e) {
			throw new DoxTemplateException("Error while fetching templates!", e);
		}
		return new ResponseEntity<String>("Template Successfully deleted with name : " + templateName, HttpStatus.OK);
	}

	@ApiOperation(value = "Download template in file format")
	@GetMapping("/download/{templateName}")
	@ResponseStatus
	public ResponseEntity<Resource> downloadTemplateByName(@PathVariable(name = "templateName") String templateName)
			throws DoxTemplateException {
		Path path;
		ByteArrayResource resource = null;
		HttpHeaders headers = new HttpHeaders();
		log.info("Downloading template for template, Name: " + templateName);
		try {
			
			Template template = dbUtil.findTemplate(templateName);
			if (template != null) {
				JSONObject json = template.getTemplateJson();
				json.put(CONSTANTS.TEMPLATE_DOWNLOAD_NAMESTRING, templateName);
				path = Paths.get(properties.getTemplateDownloadLocation(),
						templateName.concat(".").concat(properties.getTemplateDownloadFormat()));
				util.jsonToCsv(json, path.toFile());
				resource = new ByteArrayResource(Files.readAllBytes(path));

				headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
						+ templateName.concat(".").concat(properties.getTemplateDownloadFormat()));
				
				path.toFile().delete();
			} else {
				return ResponseEntity.badRequest().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
						.body(resource);
			}

		} catch (Exception e) {
			throw new DoxTemplateException("Error while fetching templates!", e);
		}
		return ResponseEntity.ok().headers(headers).contentLength(path.toFile().length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}
}
