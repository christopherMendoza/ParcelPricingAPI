package com.mynt.parcelpricing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Main entry point class for this project
 * 
 * @author Cristopher Mendoza
 * @since 4/27/2021
 */

@ComponentScan
@SpringBootApplication
public class ParcelPricingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelPricingServiceApplication.class, args);
	}
	
	@Bean
	public WebClient.Builder getWebClientBuilder(){
		return WebClient.builder();
	}
}