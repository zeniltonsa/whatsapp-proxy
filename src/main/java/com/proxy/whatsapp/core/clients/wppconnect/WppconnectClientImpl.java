package com.proxy.whatsapp.core.clients.wppconnect;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.function.SingletonSupplier;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.proxy.whatsapp.core.clients.AuthenticationInterceptor;
import com.proxy.whatsapp.core.clients.LogInterceptor;
import com.proxy.whatsapp.core.clients.RetryableOnFailureAuthorizationInterceptor;
import com.proxy.whatsapp.core.clients.wppconnect.dto.RequestCreateSession;
import com.proxy.whatsapp.core.clients.wppconnect.dto.RequestSendMessage;
import com.proxy.whatsapp.core.clients.wppconnect.dto.ResponseSendMessage;
import com.proxy.whatsapp.core.clients.wppconnect.dto.ResponseStarSession;
import com.proxy.whatsapp.core.clients.wppconnect.dto.ResponseStatusSession;
import com.proxy.whatsapp.core.clients.wppconnect.dto.WppconnectToken;
import com.proxy.whatsapp.exceptions.WhatsAppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ConditionalOnProperty(name = "whatsapp.wppconnect.enabled", havingValue = "true", matchIfMissing = false)
@Service("wpp")
public class WppconnectClientImpl implements WppconnectClient {

	private final SingletonSupplier<WppconnectService> wppConnectService;
	private final SingletonSupplier<WppconnectService> wppConnectTokenService;

	private final AtomicReference<WppconnectToken> cachedToken;
	private final Supplier<WppconnectToken> createNewToken;

	private final WppconnectProperties properties;

	public WppconnectClientImpl(WppconnectProperties properties, CloseableHttpClient httpClient) {
		log.info("[WPPConnect Server]: Iniciando");
		this.properties = properties;
		this.cachedToken = new AtomicReference<>();
		this.wppConnectTokenService = SingletonSupplier.of(() -> initializeTokenService(properties, httpClient));
		this.createNewToken = this::generateToken;
		this.wppConnectService = SingletonSupplier.of(() -> initializeService(properties, httpClient));
	}

	public WppconnectToken generateToken() {
		try {
			return wppConnectTokenService.obtain().generateToken(properties.getSession(), properties.getSecretkey());
		} catch (RestClientResponseException ex) {
			String details = ex.getResponseBodyAsString();
			throw new WhatsAppException("Error generating token. Details: " + details, ex);
		}
	}

	public ResponseStarSession startSession() {
		try {
			return wppConnectService.obtain().startSession(properties.getSession(), new RequestCreateSession("", true));
		} catch (RestClientResponseException ex) {
			throw new WhatsAppException("Error generating session.", ex);
		}
	}

	public ResponseStatusSession statusSession() {
		try {
			return wppConnectService.obtain().statusSession(properties.getSession());
		} catch (RestClientResponseException ex) {
			throw new WhatsAppException("Error generating QRCode.", ex);
		}
	}

	public ResponseStatusSession logoutSession() {
		try {
			return wppConnectService.obtain().logoutSession(properties.getSession());
		} catch (RestClientResponseException ex) {
			throw new WhatsAppException("Error generating QRCode.", ex);
		}
	}

	public ResponseStatusSession closeSession() {
		try {
			return wppConnectService.obtain().closeSession(properties.getSession());
		} catch (RestClientResponseException ex) {
			throw new WhatsAppException("Error generating QRCode.", ex);
		}
	}

	public ResponseSendMessage sendMessage(String toPhoneNumber, String message) {
		try {
			var request = RequestSendMessage.builder().phone(toPhoneNumber).message(message).build();
			return wppConnectService.obtain().sendMessage(properties.getSession(), request);
		} catch (RestClientResponseException ex) {
			throw new WhatsAppException("Error generating session.", ex);
		}
	}

	public WppconnectToken getToken() {
		if (isTokenExpired()) {
			synchronized (this) {
				if (isTokenExpired()) {
					cachedToken.set(createNewToken.get());
				}
			}
		}

		return cachedToken.get();
	}

	private boolean isTokenExpired() {
		return cachedToken.get() == null || cachedToken.get().getToken() == null;
	}

	private WppconnectService initializeService(WppconnectProperties properties, CloseableHttpClient httpClient) {

		final var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setConnectionRequestTimeout(Duration.ofMinutes(2));
		requestFactory.setConnectionRequestTimeout(Duration.ofMinutes(2));

		var restClient = RestClient.builder() //
				.baseUrl(properties.getApiUrl()) //
				.requestInterceptor(AuthenticationInterceptor.with(this::getToken)) //
				.requestInterceptor(RetryableOnFailureAuthorizationInterceptor.with(oldToken -> getToken())).build();

		RestClientAdapter adapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

		return factory.createClient(WppconnectService.class);
	}

	private WppconnectService initializeTokenService(WppconnectProperties properties, CloseableHttpClient httpClient) {

		final var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setConnectionRequestTimeout(Duration.ofMinutes(2));
		requestFactory.setConnectionRequestTimeout(Duration.ofMinutes(2));

		var restClient = RestClient.builder() //
				.baseUrl(properties.getApiUrl()) //
				.requestInterceptor(LogInterceptor.with()) //
				.build();

		RestClientAdapter adapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

		return factory.createClient(WppconnectService.class);
	}

}
