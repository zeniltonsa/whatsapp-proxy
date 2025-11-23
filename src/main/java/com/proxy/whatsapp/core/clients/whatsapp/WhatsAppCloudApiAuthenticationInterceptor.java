package com.proxy.whatsapp.core.clients.whatsapp;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class WhatsAppCloudApiAuthenticationInterceptor implements ClientHttpRequestInterceptor {

	private final Supplier<String> token;

	public WhatsAppCloudApiAuthenticationInterceptor(Supplier<String> token) {
		this.token = Objects.requireNonNull(token);
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + token.get());
		return execution.execute(request, body);
	}

	public static ClientHttpRequestInterceptor with(Supplier<String> supplier) {
		return new WhatsAppCloudApiAuthenticationInterceptor(supplier);
	}

}
