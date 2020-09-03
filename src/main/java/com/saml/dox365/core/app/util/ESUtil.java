package com.saml.dox365.core.app.util;

import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import com.saml.dox365.core.app.exceptions.DoxESRequestException;

@Component
public class ESUtil {

	public SearchRequest retrieveSearchRequest(String index, JSONObject searchFields) throws DoxESRequestException{
		try {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();		
		BoolQueryBuilder booleanQueryBuilder = QueryBuilders.boolQuery();
		searchFields.forEach((key, value) -> booleanQueryBuilder.must(QueryBuilders.termQuery((String)key,value)));
		
		sourceBuilder.query(booleanQueryBuilder);
		sourceBuilder.size(5);
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices(index);
		searchRequest.source(sourceBuilder);

		return searchRequest;

		}
		catch(Exception e){
			throw new DoxESRequestException("Elastic Search Bad Search Request", e);
		}
	}
}
