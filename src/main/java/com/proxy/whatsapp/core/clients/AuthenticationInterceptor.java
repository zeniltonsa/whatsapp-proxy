package com.proxy.whatsapp.core.clients;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.proxy.whatsapp.core.clients.wppconnect.dto.WppconnectToken;

public class AuthenticationInterceptor implements ClientHttpRequestInterceptor {

	private final Supplier<WppconnectToken> token;

	public AuthenticationInterceptor(Supplier<WppconnectToken> token) {
		this.token = Objects.requireNonNull(token);
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		request.getHeaders().set(HttpHeaders.AUTHORIZATION, token.get().getAuthorization());
		return execution.execute(request, body);
	}

	public static ClientHttpRequestInterceptor with(Supplier<WppconnectToken> supplier) {
		return new AuthenticationInterceptor(supplier);
	}

}
