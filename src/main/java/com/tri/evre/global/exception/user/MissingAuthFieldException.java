package com.tri.evre.global.exception.user;

public class MissingAuthFieldException extends RuntimeException{

	public MissingAuthFieldException(String msg) {
		super(msg);
	}
	
}
