package com.proxy.whatsapp.core.clients.wppconnect.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseStatusSession {

	private String status;
	private String qrcode;

}
