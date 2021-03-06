package com.techshop.admin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Brand not found")
public class BrandNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public BrandNotFoundException(String message) {
		super(message);
	}
	 
}
