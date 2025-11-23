package com.proxy.whatsapp.core.clients.evolutionapi;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import com.proxy.whatsapp.core.clients.evolutionapi.dto.RequestSendText;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseConnectInstance;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseConnectionState;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseLogout;
import com.proxy.whatsapp.core.clients.evolutionapi.dto.ResponseSendText;

@HttpExchange(contentType = MediaType.APPLICATION_JSON_VALUE, accept = MediaType.APPLICATION_JSON_VALUE)
public interface EvolutionApiService {

	@PostExchange(url = "/instance/create")
	ResponseSendText createInstance(@PathVariable String instance, @RequestBody RequestSendText request);

	@GetExchange(url = "/instance/connect/{instance}")
	ResponseConnectInstance connectInstance(@PathVariable String instance);

	@GetExchange(url = "/instance/connectionState/{instance}")
	ResponseConnectionState connectionState(@PathVariable String instance);

	@DeleteExchange(url = "/instance/logout/{instance}")
	ResponseLogout logout(@PathVariable String instance);

	@PostExchange(url = "/message/sendText/{instance}")
	ResponseSendText sendText(@PathVariable String instance, @RequestBody RequestSendText request);

}
