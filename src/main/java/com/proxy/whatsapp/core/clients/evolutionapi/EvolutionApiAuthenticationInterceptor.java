package com.proxy.whatsapp.core.clients.evolutionapi;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class EvolutionApiAuthenticationInterceptor implements ClientHttpRequestInterceptor {

	private final Supplier<EvolutionApiKey> apiKey;

	public EvolutionApiAuthenticationInterceptor(Supplier<EvolutionApiKey> token) {
		this.apiKey = Objects.requireNonNull(token);
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		request.getHeaders().set("apikey", apiKey.get().getApiKey());
		return execution.execute(request, body);
	}

	public static ClientHttpRequestInterceptor with(Supplier<EvolutionApiKey> supplier) {
		return new EvolutionApiAuthenticationInterceptor(supplier);
	}

}
