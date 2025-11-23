package com.proxy.whatsapp.exceptions;

public class WhatsAppException extends RuntimeException {

	private static final long serialVersionUID = -6348234645380001356L;

	public WhatsAppException(String message) {
		super(message);
	}

	public WhatsAppException(String message, Throwable throwable) {
		super(message, throwable);
	}

}