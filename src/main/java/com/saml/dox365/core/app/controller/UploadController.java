package com.saml.dox365.core.app.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.saml.dox365.core.app.dao.impl.TransactionConfigRepositoryCustomImpl;
import com.saml.dox365.core.app.domain.UploadResponse;
import com.saml.dox365.core.app.repository.TransactionRepository;
import com.saml.dox365.core.app.service.UploadDocumentService;
import com.saml.dox365.core.app.util.CONSTANTS;
import com.saml.dox365.core.app.util.DBUtil;
import com.saml.dox365.core.app.util.S3Connector;
import com.saml.dox365.core.app.util.UploadDocumentHelper;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/core")
public class UploadController {
	Logger log = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	S3Connector s3Connector;

	@Autowired
	UploadDocumentHelper uploadHelper;

	@Autowired
	UploadDocumentService uploadService;

	@Autowired
	TransactionConfigRepositoryCustomImpl configRepository;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	DBUtil dbUtil;
	
	
	@ApiOperation("Process content and metadata upload request ")
	@PostMapping("/{docCategory}/upload")
	@CrossOrigin
	public Callable<UploadResponse> upload(@RequestHeader HttpHeaders httpHeaders,
			@PathVariable("docCategory") final String docCategory, @RequestPart("metaData") String metadata,
			@RequestPart("uploadFile") MultipartFile uploadFile) throws ParseException {
		return () -> {
			
			final Date ingestionDate = new Date();
			log.info("Uploading File to Object Store : {}  with ingestion Date  {}", uploadFile.getOriginalFilename(),
					ingestionDate);
			
			String docId = dbUtil.insertTransaction(httpHeaders, docCategory, CONSTANTS.STATUS_INPROGRESS);
			
			String fileType = uploadHelper.checkSupportedExtensions(uploadFile);

			String fileUriKey = uploadService.uploadContent(uploadFile.getOriginalFilename(), docCategory, uploadFile,
					docId);
			JSONObject obj = uploadHelper.initializeJson(metadata, docId, docCategory, fileType, fileUriKey);
			UploadResponse response = uploadService.ingestMetadata(obj, docCategory, docId, ingestionDate);
			dbUtil.updateTransaction(docId, CONSTANTS.STATUS_COMPLETED, "default");
			return response;
		};

	}

	@ApiOperation("Process content and metadata upload request ")
	@PostMapping("/{docCategory}/multiUpload")
	@CrossOrigin
	public Callable<UploadResponse> multiUpload(@RequestHeader HttpHeaders httpHeaders,
			@PathVariable("docCategory") final String docCategory, @RequestPart("metaData") String l_metadata,
			@RequestPart("uploadFile") MultipartFile[] l_uploadFile) throws ParseException {
		return () -> {
			final Date ingestionDate = new Date();
			List<UploadResponse> uploadResponse = new ArrayList<UploadResponse>();
			List<String> l_fileUrlKey = new ArrayList<String>();
			List<String> l_fileType = new ArrayList<String>();
			String docId = UUID.randomUUID().toString();

			for (int i = 0; i < l_uploadFile.length; i++) {
				log.info("Uploading File to Object Store : {}  with ingestion Date  {}",
						l_uploadFile[i].getOriginalFilename(), ingestionDate);

				String fileType = uploadHelper.checkSupportedExtensions(l_uploadFile[i]);
				String fileUriKey = uploadService.uploadContent(l_uploadFile[i].getOriginalFilename(), docCategory,
						l_uploadFile[i], docId);
				l_fileUrlKey.add(fileUriKey);
				l_fileType.add(fileType);
			}

			JSONObject obj = uploadHelper.initializeJson(l_metadata, docId, docCategory,
					String.join(CONSTANTS.DELIM_COMMA, l_fileType), String.join(CONSTANTS.DELIM_COMMA, l_fileUrlKey));
			UploadResponse response = uploadService.ingestMetadata(obj, docCategory, docId, ingestionDate);

			return response;
		};
	}

	@ApiOperation("Retrieve metadata and document URL")
	@PostMapping("/{docCategory}/retrieve")
	@CrossOrigin
	public ResponseEntity<ArrayList<Map<String, Object>>> retrieve(@RequestHeader HttpHeaders httpHeaders,
			@PathVariable("docCategory") final String docCategory, @RequestParam("metaData") String searchFields)
			throws Exception {
		ArrayList<Map<String, Object>> listOfJson = new ArrayList<Map<String, Object>>();
		SearchHit[] searchHit = uploadService.retrieveMetaData(docCategory, searchFields);
		for (SearchHit hit : searchHit) {
			listOfJson.add(hit.getSourceAsMap());
		}
		return ResponseEntity.ok(listOfJson);
	}

	@ApiOperation("Download Content File")
	@PostMapping("/download")
	@CrossOrigin
	public ResponseEntity<Resource> download(@RequestParam("urlPath") String urlPath) throws Exception {
		HttpHeaders headers = new HttpHeaders();

		ByteArrayResource byteArrayResource = new ByteArrayResource(
				IOUtils.toByteArray(uploadService.downloadContent(urlPath)));
		headers.setContentLength(byteArrayResource.contentLength());
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + FilenameUtils.getName(urlPath));
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(byteArrayResource);

	}

}
