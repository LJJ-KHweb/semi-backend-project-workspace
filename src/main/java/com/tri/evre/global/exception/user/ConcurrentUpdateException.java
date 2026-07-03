package com.tri.evre.global.exception.user;

public class ConcurrentUpdateException extends RuntimeException {

	public ConcurrentUpdateException(String msg) {
		super(msg);
	}

}
