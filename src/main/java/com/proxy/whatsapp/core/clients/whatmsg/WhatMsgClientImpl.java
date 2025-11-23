package com.proxy.whatsapp.core.clients.whatmsg;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.function.SingletonSupplier;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpExchangeAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.proxy.whatsapp.core.clients.WhatsAppResponse;
import com.proxy.whatsapp.core.clients.whatmsg.dto.RequestMessage;
import com.proxy.whatsapp.core.clients.whatmsg.dto.ResponseWhatMsgAcceptedMessage;
import com.proxy.whatsapp.exceptions.WhatsAppException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(name = "whatsapp.whatmsg.enabled", havingValue = "true", matchIfMissing = false)
@Service("whatmsg")
public class WhatMsgClientImpl implements WhatMsgClient {

	private final SingletonSupplier<WhatMsgService> service;
	private final WhatMsgProperties properties;

	public WhatMsgClientImpl(RestClient.Builder builder, WhatMsgProperties properties) {
		this.properties = properties;
		service = SingletonSupplier.of(() -> {

			builder.baseUrl(properties.getApiUrl());

			HttpExchangeAdapter adapter = RestClientAdapter.create(builder.build());
			HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

			return factory.createClient(WhatMsgService.class);
		});
	}

	@Override
	public WhatsAppResponse sendMessage(String toPhoneNumber, String message) {
		return this.sendMessage(toPhoneNumber, "{number}", message);
	}

	private ResponseWhatMsgAcceptedMessage sendMessage(String toPhoneNumber, String fromPhoneNumber, String message) {
		try {

			RequestMessage request = RequestMessage.builder() //
					.token(properties.getApiKey()) //
					.to(toPhoneNumber) //
					.from(fromPhoneNumber) //
					.body(message) //
					.priority("high") //
					.build();

			return service.obtain().sendMessage(request.toMultiValueMap());
		} catch (RestClientResponseException ex) {
			throw new WhatsAppException("Error sending message.", ex);
		}
	}

}
