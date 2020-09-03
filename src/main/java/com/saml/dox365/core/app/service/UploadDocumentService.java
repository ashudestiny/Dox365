package com.saml.dox365.core.app.service;

import java.util.Date;

import org.apache.http.HttpStatus;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.saml.dox365.core.app.domain.UploadResponse;
import com.saml.dox365.core.app.exceptions.DoxESException;
import com.saml.dox365.core.app.exceptions.DoxS3Exception;
import com.saml.dox365.core.app.exceptions.DoxS3KeyNotFoundException;
import com.saml.dox365.core.app.interfaces.UploadService;
import com.saml.dox365.core.app.util.CONSTANTS;
import com.saml.dox365.core.app.util.CommonUtil;
import com.saml.dox365.core.app.util.ESUtil;
import com.saml.dox365.core.app.util.S3Connector;

/**
 * 
 * @author ashish tuteja
 * 
 */
@Service
public class UploadDocumentService implements UploadService {
	Logger log = LoggerFactory.getLogger(UploadDocumentService.class);

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Autowired
	S3Connector s3Connector;

	@Autowired
	ESUtil esUtil;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	private CommonUtil util;

	public UploadResponse ingestMetadata(JSONObject obj, String index, String docId, Date ingestionDate)
			throws DoxESException {

		try {
			IndexRequest indexRequest = new IndexRequest(index, CONSTANTS.ES_DOCUMENT_TYPE,
					obj.get(CONSTANTS.DOC_ID).toString());
			indexRequest.source(obj.toString(), XContentType.JSON);

			IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
			log.debug("Response ELR ", indexResponse);

			log.info(" File Uploaded successfully for metadata Id : {} and doc ID : {}", indexResponse.getId(), docId);
			return new UploadResponse(indexResponse.getId(), docId, ingestionDate, HttpStatus.SC_CREATED,
					new JSONObject());

		} catch (Exception e) {
			log.error("Exception occured in ingestMetadata method: {}", e.getMessage());
			throw new DoxESException("Exception occured during ES ingestion", e);
		}

	}

	public String uploadContent(String fileName, String contentGroup, MultipartFile multiPartfile, String docId)
			throws DoxS3Exception {
		return s3Connector.putFile(fileName, contentGroup, multiPartfile, docId);
	}

	public S3ObjectInputStream downloadContent(String fileUrl) throws DoxS3KeyNotFoundException, DoxS3Exception {
		return s3Connector.downloadFromStream(fileUrl);
	}

	public SearchHit[] retrieveMetaData(String docCategory, String searchFields) throws DoxESException{
		SearchHit[] searchHit = null;
		try {
			JSONObject json = util.convertToJson(searchFields);
			SearchRequest searchRequest = esUtil.retrieveSearchRequest(docCategory, json);
			SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
			searchHit = searchResponse.getHits().getHits();
		} catch (Exception e) {
			throw new DoxESException(e.getMessage(), e);
		}
		return searchHit;
	}

}
