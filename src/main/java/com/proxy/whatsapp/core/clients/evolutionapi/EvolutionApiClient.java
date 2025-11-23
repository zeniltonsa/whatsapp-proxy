package com.proxy.whatsapp.core.clients.evolutionapi;

import com.proxy.whatsapp.core.clients.WhatsAppClient;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseConnectInstance;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseConnectionState;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseLogout;

public interface EvolutionApiClient extends WhatsAppClient {

	ResponseConnectInstance connectInstance();

	ResponseConnectionState connectionState();

	ResponseLogout logout();

}
