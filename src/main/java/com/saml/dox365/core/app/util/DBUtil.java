package com.saml.dox365.core.app.util;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.mongodb.MongoWriteException;
import com.saml.dox365.core.app.dao.TemplateDao;
import com.saml.dox365.core.app.dao.TransactionDao;
import com.saml.dox365.core.app.dao.impl.TransactionConfigRepositoryCustomImpl;
import com.saml.dox365.core.app.domain.Template;
import com.saml.dox365.core.app.domain.Transaction;
import com.saml.dox365.core.app.exceptions.DoxTemplateException;

@Component
public class DBUtil {

	@Autowired
	TransactionConfigRepositoryCustomImpl configRepository;
	
	@Autowired
	TransactionDao transactionDao;
	
	@Autowired
	TemplateDao templateDao;
	
	@Autowired
	CommonUtil util;
	
	public String insertTransaction(HttpHeaders httpHeaders, String docCategory, String status) {

		String docId = UUID.randomUUID().toString();
		Transaction transaction = new Transaction();
		transaction.setCreateBy("ashish");
		transaction.setCreatedDate(LocalDate.now().toString());
		transaction.setDocClass(docCategory);
		transaction.setMappingId(docId);
		transaction.setOrgName("default");
		transaction.setStatus(CONSTANTS.STATUS_INPROGRESS);

		configRepository.setCollectionName(docCategory);
		transactionDao.insertTransaction(transaction);
		return docId;
	}
	
	public void updateTransaction(String docId, String status, String orgName) {
		transactionDao.saveUpdateTransactionStatusById(docId, status, orgName);
	}
	
	public void insertTemplate(String templateName, String fieldJson, String orgName) throws DoxTemplateException, DuplicateKeyException{
		try {
		Template template = new Template();
		template.setOrgName(orgName);
		template.setTemplateJson(util.convertToJson(fieldJson));
		template.setTemplateName(templateName);
		templateDao.insertTemplate(template);
		}
		catch(Exception e) {
			if(e.getLocalizedMessage().toLowerCase().contains("duplicate key error")) {
				throw new DuplicateKeyException(e.getLocalizedMessage());
			}
			else {
				throw new DoxTemplateException(e.getMessage(), e);
			}
		}
	}
	
	public List<Template> findAllTemplate() throws DoxTemplateException {
		try {
			return templateDao.findAllWithSortTemplate();
		} catch (Exception e) {
			throw new DoxTemplateException("Error while fetching templates!", e);
		}
	}
	
	public void deleteTemplate(String templateName) throws DoxTemplateException{
		try {
			templateDao.deleteTemplateByName(templateName);
		} catch (Exception e) {
			throw new DoxTemplateException("Error while deleting template!", e);
		}
	}
	
	public Template findTemplate(String templateName) throws DoxTemplateException{
		try {
			return templateDao.findOneTemplateByName(templateName);
		} catch (Exception e) {
			throw new DoxTemplateException("Error while fetching template!", e);
		}
		
		
	}
}
