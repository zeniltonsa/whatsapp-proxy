package com.proxy.whatsapp.core.clients;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		log.info("[LogInterceptor]: Url: {} , Method: {}, Headers: {}", request.getURI(), request.getMethod(),
				request.getHeaders());
		return execution.execute(request, body);
	}

	public static ClientHttpRequestInterceptor with() {
		return new LogInterceptor();
	}

}
