package com.proxy.whatsapp.core.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.core5.http.io.SocketConfig;
import org.apache.hc.core5.io.CloseMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;

@Configuration
public class ApacheHttpClientConfiguration {

	private CloseableHttpClient httpClient5;

	@Bean
	CloseableHttpClient httpClient5(HttpClientConnectionManager connectionManager) {

		httpClient5 = HttpClients.custom() //
				.useSystemProperties() //
				.disableCookieManagement() //
				.setConnectionManager(connectionManager) //
				.build();

		return httpClient5;
	}

	@Bean
	HttpClientConnectionManager connectionManager() {

		return PoolingHttpClientConnectionManagerBuilder.create() //
				.setDefaultSocketConfig( //
						SocketConfig.custom() //
								.setSoKeepAlive(true) //
								.setSoReuseAddress(true) //
								.build())
				.build();
	}

	@PreDestroy
	public void destroy() {
		if (httpClient5 != null) {
			httpClient5.close(CloseMode.GRACEFUL);
		}
	}

}
