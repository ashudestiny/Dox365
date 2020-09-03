package com.saml.dox365.core.app.dummy;

import javax.annotation.PostConstruct;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saml.dox365.core.app.util.CommonUtil;
import com.saml.dox365.core.app.util.S3Connector;

@Component
public class Test {
	
	@Autowired
	CommonUtil util;
	
	
	
	public void init() throws Exception {
		String json = "{\"test\":\"test\",\"test1\":\"test1\"}";
		String path = "C:\\Users\\ashish\\Desktop\\test.csv";
		//util.jsonToCsv(json, path);
	}

}
