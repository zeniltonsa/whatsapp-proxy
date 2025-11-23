package com.proxy.whatsapp.core.clients.whatsapp;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.proxy.whatsapp.core.clients.whatsapp.dto.RequestMessage;
import com.proxy.whatsapp.core.clients.whatsapp.dto.ResponseWhatsAppAcceptedMessage;

@HttpExchange(contentType = MediaType.APPLICATION_JSON_VALUE, accept = MediaType.APPLICATION_JSON_VALUE)
public interface WhatsAppCloudApiService {

	@PostExchange(url = "https://graph.facebook.com/v22.0/{phoneNumberId}/messages")
	ResponseWhatsAppAcceptedMessage enviarMensagens(@PathVariable String phoneNumberId,
			@RequestBody RequestMessage request);

}
