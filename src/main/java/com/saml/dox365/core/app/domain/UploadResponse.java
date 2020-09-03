package com.saml.dox365.core.app.domain;

import java.util.Date;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Component
public class UploadResponse {
	private String doc_id;
	private String record_id;
	private Date receivedDate;
	private int status;	
	private JSONObject errorMap ;
	
	public UploadResponse() {
		
	}
	
}
