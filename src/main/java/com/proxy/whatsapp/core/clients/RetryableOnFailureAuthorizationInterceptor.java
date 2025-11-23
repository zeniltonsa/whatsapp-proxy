package com.proxy.whatsapp.core.clients;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.EnumSet;
import java.util.Objects;
import java.util.function.UnaryOperator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.BackOffExecution;
import org.springframework.util.backoff.FixedBackOff;

import com.proxy.whatsapp.core.clients.wppconnect.dto.WppconnectToken;

public class RetryableOnFailureAuthorizationInterceptor implements ClientHttpRequestInterceptor {

	private static final FixedBackOff DEFAULT_BACKOFF = new FixedBackOff(400, 4);

	private static final EnumSet<HttpStatus> DEFAULT_RETRYABLE_RESPONSE_STATUS = EnumSet.of( //
			HttpStatus.TOO_EARLY, //
			HttpStatus.UNAUTHORIZED, //
			HttpStatus.GATEWAY_TIMEOUT, //
			HttpStatus.REQUEST_TIMEOUT, //
			HttpStatus.TOO_MANY_REQUESTS, //
			HttpStatus.SERVICE_UNAVAILABLE, //
			HttpStatus.INTERNAL_SERVER_ERROR //
	);

	private final BackOff backOffPolicy;
	private final UnaryOperator<WppconnectToken> revokeAndGetNewToken;

	public RetryableOnFailureAuthorizationInterceptor(BackOff backOffPolicy,
			UnaryOperator<WppconnectToken> revokeAndGetNewToken) {
		this.backOffPolicy = Objects.requireNonNull(backOffPolicy);
		this.revokeAndGetNewToken = Objects.requireNonNull(revokeAndGetNewToken);
	}

	public static RetryableOnFailureAuthorizationInterceptor with(UnaryOperator<WppconnectToken> revokeAndGetNewToken) {
		return new RetryableOnFailureAuthorizationInterceptor(DEFAULT_BACKOFF, revokeAndGetNewToken);
	}

	public static RetryableOnFailureAuthorizationInterceptor with(BackOff backOffPolicy,
			UnaryOperator<WppconnectToken> revokeAndGetNewToken) {
		return new RetryableOnFailureAuthorizationInterceptor(backOffPolicy, revokeAndGetNewToken);
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		var backoff = backOffPolicy.start();
		ClientHttpResponse response = null;
		do {
			try {
				response = execution.execute(request, body);

				if (response.getStatusCode().is2xxSuccessful() || !isRetryable(response)) {
					return response;
				} else if (is401Response(response)) {
					var currentToken = StringUtils.stripToNull(request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
							.split(" ")[1];

					request.getHeaders().remove(HttpHeaders.AUTHORIZATION);
					request.getHeaders().add(HttpHeaders.AUTHORIZATION, //
							revokeAndGetNewToken //
									.andThen(WppconnectToken::getAuthorization)
									.apply(WppconnectToken.bearerToken(currentToken)));
				}

			} catch (IOException ex) {
				if (!isRetryableException(ex)) {
					throw ex;
				}
			}
		} while (isContinue(backoff));
		throw new ExhaustedRetryException(response);
	}

	private boolean isContinue(BackOffExecution backoff) {

		var timeToWait = backoff.nextBackOff();

		if (timeToWait != BackOffExecution.STOP) {
			try {
				Thread.sleep(timeToWait);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
			return true;
		}
		return false;
	}

	private boolean is401Response(ClientHttpResponse response) throws IOException {
		return HttpStatus.UNAUTHORIZED.isSameCodeAs(response.getStatusCode());
	}

	private boolean isRetryable(ClientHttpResponse response) throws IOException {
		HttpStatusCode statusCode = response.getStatusCode();
		return DEFAULT_RETRYABLE_RESPONSE_STATUS.stream().anyMatch(httpStatus -> httpStatus.isSameCodeAs(statusCode));
	}

	private boolean isRetryableException(IOException ex) {
		return ex instanceof SocketTimeoutException || ex instanceof ConnectException;
	}

}
