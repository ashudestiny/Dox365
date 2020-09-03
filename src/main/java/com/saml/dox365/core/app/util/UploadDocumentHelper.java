package com.saml.dox365.core.app.util;

import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Component
public class UploadDocumentHelper {
	
	Logger log = LoggerFactory.getLogger(UploadDocumentHelper.class);
	
	@Autowired
	private CommonUtil util;
	
	public String checkSupportedExtensions(MultipartFile uploadFile) throws Exception {
		String extension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
		
		if (!"jpg,jpeg,xlsx,xls,txt,pdf".contains(extension.toLowerCase())) {
			throw new Exception();
		}
		return extension;
	}

	public JSONObject initializeJson(String metaDataJson, String docId, String documentGroup, String fileType, String fileUrl) throws ParseException {
		JSONObject jsonObj = null;
		try {
			jsonObj = util.convertToJson(metaDataJson);
			log.info("JSON Request for DOC_ID  : {}" , docId );
			jsonObj.put(CONSTANTS.DOC_ID, docId);
			jsonObj.put(CONSTANTS.FILE_TYPE, fileType);
			jsonObj.put(CONSTANTS.URL_PATH, fileUrl);
			jsonObj.put(CONSTANTS.DOCUMENT_GROUP, documentGroup);
		}
		catch(ParseException e) {
			log.error("Error occured during parsing Meta data : {} \n {}", metaDataJson, e);
			throw e;
		} 
		return jsonObj;
	}
	
	
	
	
}


