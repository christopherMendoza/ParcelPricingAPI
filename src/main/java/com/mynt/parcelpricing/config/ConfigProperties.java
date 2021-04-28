package com.mynt.parcelpricing.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class ConfigProperties {

	private String voucherServiceApi;
	private String voucherServiceApiKey;
}
