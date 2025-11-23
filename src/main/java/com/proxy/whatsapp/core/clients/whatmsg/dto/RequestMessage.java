package com.proxy.whatsapp.core.clients.whatmsg.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class RequestMessage {

	private String token;

	@JsonProperty("to")
	private String to;

	@JsonProperty("from")
	private String from;

	@JsonProperty("msgbody")
	private String body;

	@JsonProperty
	private String priority;

	public MultiValueMap<String, String> toMultiValueMap() {
		var formData = new LinkedMultiValueMap<String, String>();
		formData.add("token", token);
		formData.add("to", cleanPhoneNumber(to));
		formData.add("from", cleanPhoneNumber(from));
		formData.add("body", body);
		formData.add("priority", priority);

		return formData;
	}

	private String cleanPhoneNumber(String phoneNumber) {
		if (!StringUtils.hasText(phoneNumber)) {
			return null;
		}

		int tamanhoPrefixo = 3;
		int posicaoDeTras = 9;
		String resultadoFinal = "";

		String prefixo = phoneNumber.substring(0, tamanhoPrefixo);
		String numeroLocal = phoneNumber.substring(tamanhoPrefixo);

		if (numeroLocal.length() >= posicaoDeTras) {
			int indexParaRemover = numeroLocal.length() - posicaoDeTras;
			String novoNumeroLocal = numeroLocal.substring(0, indexParaRemover)
					+ numeroLocal.substring(indexParaRemover + 1);
			resultadoFinal = prefixo + novoNumeroLocal;
		}

		return resultadoFinal;
	}

}