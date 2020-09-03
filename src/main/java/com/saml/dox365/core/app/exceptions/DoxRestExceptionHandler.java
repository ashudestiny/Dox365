package com.saml.dox365.core.app.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DoxRestExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(value = { DoxS3KeyNotFoundException.class})
	protected ResponseEntity<Object> handleMissing(Exception ex, WebRequest request) {
		String bodyOfResponse = "Wrong file url";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(value = {DuplicateKeyException.class})
	protected ResponseEntity<Object> handleMongoDupKey(Exception ex, WebRequest request) {
		String bodyOfResponse = "Template with same name already exists!";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value = { DoxS3Exception.class, DoxESException.class, DoxTemplateException.class})
	protected ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest request) {
		String bodyOfResponse = "OOPS! Something went wrong";
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
