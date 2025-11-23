package com.proxy.whatsapp.core.clients.evolutionapi;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.proxy.whatsapp.core.clients.evolutionapi.dto.Instance;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseConnectInstance;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseConnectionState;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseLogout;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseSendText;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseSendText.Key;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseSendText.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(name = "whatsapp.evolution-api.enabled", havingValue = "false", matchIfMissing = false)
@Service("evolution")
public class EvolutionApiClientMockImpl implements EvolutionApiClient {

	public EvolutionApiClientMockImpl() {
		log.info("[Evolution Api]: Starting mock service");
	}

	@Override
	public ResponseSendText sendMessage(String toPhoneNumber, String message) {

		log.info("[EvolutionApiClientMockImpl]: {}", message);

		return ResponseSendText.builder().status("DELIVERED")
				.key(Key.builder().remoteJid(toPhoneNumber + "@s.whatsapp.net").build())
				.message(Message.builder().conversation(message).build()).build();
	}

	@Override
	public ResponseConnectInstance connectInstance() {
		return ResponseConnectInstance.builder().build();
	}

	@Override
	public ResponseConnectionState connectionState() {
		return ResponseConnectionState.builder()
				.instance(Instance.builder().instanceName("teste").state("open").build()).build();
	}

	@Override
	public ResponseLogout logout() {
		return ResponseLogout.builder().build();
	}

}
