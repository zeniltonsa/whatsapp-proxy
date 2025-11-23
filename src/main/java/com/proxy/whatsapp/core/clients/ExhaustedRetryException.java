package com.proxy.whatsapp.core.clients;

import org.springframework.core.NestedRuntimeException;
import org.springframework.http.client.ClientHttpResponse;

import lombok.NonNull;

public class ExhaustedRetryException extends NestedRuntimeException {

	private static final long serialVersionUID = -6339167787942792339L;

	@NonNull
	private final transient ClientHttpResponse response;

	public ExhaustedRetryException(ClientHttpResponse response) {
		this("Maximum number of attempts reached.", response, null);
	}

	public ExhaustedRetryException(String msg, ClientHttpResponse response, Throwable cause) {
		super(msg, cause);
		this.response = response;
	}

}
