package com.mynt.parcelpricing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mynt.parcelpricing.config.ConfigProperties;
import com.mynt.parcelpricing.exception.ApiRequestException;
import com.mynt.parcelpricing.model.VoucherItem;

import reactor.core.publisher.Mono;

/**
 * Service class that handles the voucher code retrieval and the computation.
 * 
 * @author Chris
 * @since 4/27/2021
 */
@Service
public class VoucherService {

	@Autowired
	private WebClient.Builder webClientBuilder;

	@Autowired
	private ConfigProperties configProps;

	/**
	 * Gets the voucher details from the the mock API mynt service.
	 * 
	 * @param voucherCode - the voucher code parameter.
	 * @return the VoucherItem object.
	 */
	public VoucherItem getVoucher(String voucherCode) {

		String uri = configProps.getVoucherServiceApi() + voucherCode + "?key=" + configProps.getVoucherServiceApiKey();
		VoucherItem voucher = webClientBuilder.build()
				.get().uri(uri)
				.retrieve().onStatus(HttpStatus::is4xxClientError, response->{
					Mono<String> errorMsg = response.bodyToMono(String.class);
					return errorMsg.flatMap(msg->{
						throw new ApiRequestException(msg);
					});
				})
				.onStatus(HttpStatus::is5xxServerError, response->{
					Mono<String> errorMsg = response.bodyToMono(String.class);
					return errorMsg.flatMap(msg->{
						throw new ApiRequestException(msg);
					});
				})
				.bodyToMono(VoucherItem.class)
				.block();

		return voucher;
	}
}