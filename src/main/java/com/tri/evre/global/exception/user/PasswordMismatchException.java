package com.tri.evre.global.exception.user;

public class PasswordMismatchException extends RuntimeException{

	public PasswordMismatchException(String msg) {
		super(msg);
	}

}
