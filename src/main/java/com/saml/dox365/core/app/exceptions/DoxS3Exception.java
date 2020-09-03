package com.saml.dox365.core.app.exceptions;

/**
 * @author ashish tuteja
 * S3 specific exception for Dox365
 */
@SuppressWarnings("serial")
public class DoxS3Exception extends Exception{
	
	public DoxS3Exception(String message, Throwable cause) {
		super(message, cause);
	}

}
