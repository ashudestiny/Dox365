package com.saml.dox365.core.app.exceptions;

/**
 * 
 * @author ashish tuteja
 * Elastic search specific exception for Dox365
 */
@SuppressWarnings("serial")
public class DoxESRequestException extends Exception{
	
	public DoxESRequestException(String message, Throwable cause) {
		super(message, cause);
	}

}
