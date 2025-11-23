package com.proxy.whatsapp.core.clients.evolutionapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestSendText {

	private String number;

	private String text;

}
