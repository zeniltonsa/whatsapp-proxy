package com.proxy.whatsapp.core.clients.evolutionapi;

import java.time.Duration;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.function.SingletonSupplier;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.proxy.whatsapp.core.clients.LogInterceptor;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.RequestSendText;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseConnectInstance;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseConnectionState;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseLogout;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseSendText;
import com.proxy.whatsapp.exceptions.WhatsAppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(name = "whatsapp.evolution-api.enabled", havingValue = "true", matchIfMissing = true)
@Service("evolution")
public class EvolutionApiClientImpl implements EvolutionApiClient {

	private final SingletonSupplier<EvolutionApiService> service;
	private final EvolutionApiProperties properties;

	public EvolutionApiClientImpl(CloseableHttpClient httpClient, EvolutionApiProperties properties) {
		this.service = SingletonSupplier.of(() -> initializeService(httpClient));
		this.properties = properties;
		log.info("[Evolution API]: Starting service");
	}

	public ResponseConnectInstance connectInstance() {
		try {
			return service.obtain().connectInstance(properties.getInstance());
		} catch (RestClientResponseException ex) {
			throw new WhatsAppException("Error creating instance.", ex);
		}
	}

	public ResponseConnectionState connectionState() {
		try {
			return service.obtain().connectionState(properties.getInstance());
		} catch (RestClientResponseException ex) {
			throw new WhatsAppException("Error checking instance status.", ex);
		}
	}

	public ResponseLogout logout() {
		try {
			return service.obtain().logout(properties.getInstance());
		} catch (RestClientResponseException ex) {
			throw new WhatsAppException("Error closing instance.", ex);
		}
	}

	@Override
	public ResponseSendText sendMessage(String toPhoneNumber, String message) {
		try {
			var request = RequestSendText.builder().number(toPhoneNumber).text(message).build();
			return service.obtain().sendText(properties.getInstance(), request);
		} catch (RestClientResponseException ex) {
			throw new WhatsAppException("Error sending message.", ex);
		}
	}

	private EvolutionApiKey getToken() {
		return new EvolutionApiKey(properties.getApiKey());
	}

	private EvolutionApiService initializeService(CloseableHttpClient httpClient) {

		final var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setConnectionRequestTimeout(Duration.ofMinutes(2));
		requestFactory.setConnectionRequestTimeout(Duration.ofMinutes(2));

		var restClient = RestClient.builder() //
				.baseUrl(properties.getApiUrl()) //
				.requestInterceptor(EvolutionApiAuthenticationInterceptor.with(this::getToken)) //
				.requestInterceptor(LogInterceptor.with()) //
				.build();

		RestClientAdapter adapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

		return factory.createClient(EvolutionApiService.class);
	}

}
