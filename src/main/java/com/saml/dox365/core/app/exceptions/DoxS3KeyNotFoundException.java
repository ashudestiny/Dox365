package com.saml.dox365.core.app.exceptions;

/**
 * @author ashish tuteja
 * S3 key not found exception - specific exception for Dox365
 */
@SuppressWarnings("serial")
public class DoxS3KeyNotFoundException extends Exception{
	
	public DoxS3KeyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
