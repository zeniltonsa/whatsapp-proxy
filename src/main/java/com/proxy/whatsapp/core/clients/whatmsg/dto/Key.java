package com.proxy.whatsapp.core.clients.whatmsg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Key {

	private String remoteJid;
	private boolean fromMe;
	private String id;

}
