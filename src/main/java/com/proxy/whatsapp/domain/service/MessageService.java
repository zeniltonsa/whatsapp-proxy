package com.proxy.whatsapp.domain.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.proxy.whatsapp.core.clients.WhatsAppClient;
import com.proxy.whatsapp.core.clients.WhatsAppResponse;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageService {

	private final Map<String, WhatsAppClient> clients;

	public WhatsAppResponse testMessage(String clientName, String number, String message) {
		WhatsAppClient client = clients.get(clientName);

		if (client == null) {
			throw new IllegalArgumentException("WhatsApp client not found: " + clientName);
		}

		return client.sendMessage(number, message);
	}

}
