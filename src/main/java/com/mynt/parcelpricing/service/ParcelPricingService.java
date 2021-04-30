package com.mynt.parcelpricing.service;

import java.util.List;

import com.mynt.parcelpricing.bean.ParcelBean;
import com.mynt.parcelpricing.bean.PaymentBean;
import com.mynt.parcelpricing.exception.ApiRequestException;
import com.mynt.parcelpricing.model.MathEquation;

/**
 * Service class that handles the main logic of Parcel pricing computation.
 * 
 * @author Chris
 * @since 4/27/2021
 *
 */
public interface ParcelPricingService {

	public PaymentBean compute(ParcelBean bean) throws ApiRequestException;

	public List<MathEquation> equationlist() throws ApiRequestException;
}