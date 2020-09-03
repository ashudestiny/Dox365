package com.saml.dox365.core.app.domain;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author vbaveja
 * IndexRequest with file metadata to be indexed.
 */

@Getter
@Setter
@AllArgsConstructor
@JsonDeserialize(builder = IndexRequest.MetadataBuilder.class)
@Builder(builderClassName = "MetadataBuilder", toBuilder = true)
public class IndexRequest {

	public String docId;
	public String metadataId;
	public long ingestionDate;
	public String securityGroup;
	public String fileName;
	public String fileType;

	@JsonPOJOBuilder(withPrefix = "")
	public static class MetadataBuilder {
		
		@JsonAnySetter
		Map<String, Object> details = new LinkedHashMap<>();

	}

	

}
