package com.proxy.whatsapp.core.clients.whatsapp;

import java.time.Duration;
import java.util.List;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.function.SingletonSupplier;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.proxy.whatsapp.core.clients.LogInterceptor;
import com.proxy.whatsapp.core.clients.WhatsAppResponse;
import com.proxy.whatsapp.core.clients.whatsapp.dto.RequestMessage;
import com.proxy.whatsapp.core.clients.whatsapp.dto.TemplateCobrancaAmigavel;
import com.proxy.whatsapp.core.clients.whatsapp.dto.TemplateComponents;
import com.proxy.whatsapp.core.clients.whatsapp.dto.TemplateLanguage;
import com.proxy.whatsapp.core.clients.whatsapp.dto.TemplateParameters;
import com.proxy.whatsapp.exceptions.WhatsAppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(name = "whatsapp.cloud-api.enabled", havingValue = "true", matchIfMissing = false)
@Service("cloud")
public class WhatsAppCloudApiClientImpl implements WhatsAppCloudApiClient {

	private final SingletonSupplier<WhatsAppCloudApiService> service;
	private final WhatsAppCloudApiProperties properties;

	public WhatsAppCloudApiClientImpl(CloseableHttpClient httpClient, WhatsAppCloudApiProperties properties) {
		this.service = SingletonSupplier.of(() -> initializeService(httpClient));
		this.properties = properties;
		log.info("[WhatsApp Cloud API]: Starting service");
	}

	/**
	 * @param message unsed
	 */
	@Override
	public WhatsAppResponse sendMessage(String toPhoneNumber, String message) {
		try {

			var language = new TemplateLanguage("pt_BR");
			var param = new TemplateParameters("param name", "text", "");
			var parameters = new TemplateComponents("body", List.of(param));
			var template = new TemplateCobrancaAmigavel("template name", language, List.of(parameters));
			var request = new RequestMessage("whatsapp", toPhoneNumber, "template", template);

			return service.obtain().enviarMensagens(properties.getPhoneNumberId(), request);

		} catch (RestClientResponseException ex) {

			if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				// token inválido → notifique e pare o envio
				log.error("Invalid token, renew in Business Manager");
			}

			throw new WhatsAppException("Error sending message.", ex);
		}
	}

	private String getToken() {
		return properties.getToken();
	}

	private WhatsAppCloudApiService initializeService(CloseableHttpClient httpClient) {

		final var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
		requestFactory.setConnectionRequestTimeout(Duration.ofMinutes(2));
		requestFactory.setConnectionRequestTimeout(Duration.ofMinutes(2));

		var restClient = RestClient.builder() //
				.baseUrl(properties.getApiUrl()) //
				.requestInterceptor(WhatsAppCloudApiAuthenticationInterceptor.with(this::getToken)) //
				.requestInterceptor(LogInterceptor.with()) //
				.build();

		RestClientAdapter adapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

		return factory.createClient(WhatsAppCloudApiService.class);
	}

}
