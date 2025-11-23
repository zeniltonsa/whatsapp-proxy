package com.proxy.whatsapp.core.clients.wppconnect.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseStarSession {

	private String status;
	private String qrcode;
	private String urlcode;
	private String session;

}
