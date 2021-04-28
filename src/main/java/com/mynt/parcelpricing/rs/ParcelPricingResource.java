package com.mynt.parcelpricing.rs;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mynt.parcelpricing.bean.ParcelBean;
import com.mynt.parcelpricing.bean.PaymentBean;
import com.mynt.parcelpricing.model.MathEquation;
import com.mynt.parcelpricing.service.ParcelPricingService;

/**
 * Resource API to compute parcel delivery cost.
 * 
 * @author Cristopher Mendoza
 * @since 4/27/2021
 */
@RestController
public class ParcelPricingResource {
	
	@Autowired
	private ParcelPricingService parcelPricingService;
	
	@PostMapping("/compute")
	public PaymentBean compute(@RequestBody @Valid ParcelBean bean) {
		return parcelPricingService.compute(bean);
	}
	
	@GetMapping("/equation-list")
	public List<MathEquation> list() throws IOException {
		return parcelPricingService.equationlist();
	}
}
