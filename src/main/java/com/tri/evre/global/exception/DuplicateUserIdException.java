package com.tri.evre.global.exception;

public class DuplicateUserIdException extends RuntimeException{
	public DuplicateUserIdException (String message) {
		super(message);
	}
}
