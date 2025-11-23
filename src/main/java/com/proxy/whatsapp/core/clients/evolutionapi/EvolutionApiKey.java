package com.proxy.whatsapp.core.clients.evolutionapi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvolutionApiKey {

	@Setter
	private String apiKey;

	public String getApiKey() {
		return apiKey;
	}
}
