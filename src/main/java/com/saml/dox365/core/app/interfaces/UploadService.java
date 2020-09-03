package com.saml.dox365.core.app.interfaces;

import java.util.Date;

import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.saml.dox365.core.app.domain.UploadResponse;
import com.saml.dox365.core.app.exceptions.DoxESException;
import com.saml.dox365.core.app.exceptions.DoxS3Exception;

/**
 * 
 * @author ashish tuteja
 * Upload Service (S3 and ES)
 */
public interface UploadService {

	public UploadResponse ingestMetadata(JSONObject obj, String index, String docId, Date ingestionDate)
			throws DoxESException;

	public String uploadContent(String fileName, String contentGroup, MultipartFile multiPartfile, String docId)
			throws DoxS3Exception;
}
