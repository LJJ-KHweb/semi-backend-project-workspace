package com.tri.evre.global.exception.shop;

public class InsufficientInventoryException extends RuntimeException {

	public InsufficientInventoryException(String msg) {
		super(msg);
	}

}
