package com.saml.dox365.core.app.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author ashish tuteja
 * Common used methods
 */

@Component
public class CommonUtil {

	Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	/**
	 * 
	 * @param json - JSon string to parse
	 * @return convert json format
	 * @throws ParseException
	 */
	public JSONObject convertToJson(String json) throws ParseException {
		JSONParser parser = null;
		JSONObject jsonObj = null;
		try {
			parser = new JSONParser();
			jsonObj = (JSONObject)parser.parse(json);
		}
		catch(ParseException e) {
			log.error("Error occured during parsing Meta data : {} \n {}", json, e);
			throw e;
		} 
		return jsonObj;
	}
	
	
	/**
	 * 
	 * @param jsonString Json value in String format
	 * @param filePath Destination file path where json will get written
	 * @throws Exception 
	 */

	public void jsonToCsv(JSONObject jsonObject, File file) throws Exception{
        
        try {
        	//JSONObject jsonObject = convertToJson(jsonString);
        	org.json.JSONArray jsonArray = new org.json.JSONArray();
        	jsonArray.put(jsonObject);
        	
        	String csv = CDL.toString(jsonArray);
            FileUtils.writeStringToFile(file, csv, "ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }        
    }
}
