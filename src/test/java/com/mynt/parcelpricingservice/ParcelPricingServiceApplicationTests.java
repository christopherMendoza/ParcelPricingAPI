package com.mynt.parcelpricingservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mynt.parcelpricing.service.ParcelPricingService;

@SpringBootTest
class ParcelPricingServiceApplicationTests {

	@Autowired
	ParcelPricingService service;
	
	@Test
	void testCompute() {
		
	}
}
