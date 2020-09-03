package com.saml.dox365.core.app.exceptions;

/**
 * 
 * @author ashish tuteja
 * Elastic search specific exception for Dox365
 */
@SuppressWarnings("serial")
public class DoxESException extends Exception{
	
	public DoxESException(String message, Throwable cause) {
		super(message, cause);
	}

}
