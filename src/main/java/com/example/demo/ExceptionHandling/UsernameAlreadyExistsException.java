package com.example.demo.ExceptionHandling;

public class UsernameAlreadyExistsException extends RuntimeException {

	 public UsernameAlreadyExistsException(String message) {
	        super(message);
	    }
}
