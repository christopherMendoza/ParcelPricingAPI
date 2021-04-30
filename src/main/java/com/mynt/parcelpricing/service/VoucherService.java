package com.mynt.parcelpricing.service;

import org.springframework.stereotype.Service;

import com.mynt.parcelpricing.model.VoucherItem;

/**
 * Service class that handles the voucher code retrieval and the computation.
 * 
 * @author Chris
 * @since 4/27/2021
 */
@Service
public interface VoucherService {

	/**
	 * Gets the voucher details from the the mock API mynt service.
	 * 
	 * @param voucherCode - the voucher code parameter.
	 * @return the VoucherItem object.
	 */
	public VoucherItem getVoucher(String voucherCode);
}