package com.mynt.parcelpricing.rs;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mynt.parcelpricing.bean.ParcelBean;
import com.mynt.parcelpricing.bean.PaymentBean;
import com.mynt.parcelpricing.service.ParcelPricingService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParcelPricingResourceTest {

	@Autowired
	private ParcelPricingService parcelPricingService;
	
	@Test
	public void shouldComputeCostOfDelivery() {
		ParcelBean bean = new ParcelBean(1,50,4,4, "MYNT");
		
		PaymentBean pbean = parcelPricingService.compute(bean);
		assertNotNull(pbean);
		assertEquals(pbean.getShippingFee(), formatBigDecimal(new BigDecimal(1000.00)));
		assertEquals(pbean.getDiscount(), formatBigDecimal(new BigDecimal(12.25)));
		assertEquals(pbean.getTotal(), formatBigDecimal(new BigDecimal(987.75)));
	}
	
	@Test
	public void shouldComputeCostOfDeliveryWithNoDiscount() {
		ParcelBean bean = new ParcelBean(1,50,4,4);
		
		PaymentBean pbean = parcelPricingService.compute(bean);
		assertNotNull(pbean);
		assertEquals(pbean.getShippingFee(), formatBigDecimal(new BigDecimal(1000.00)));
		assertEquals(pbean.getTotal(), formatBigDecimal(new BigDecimal(1000.00)));
		assertEquals(pbean.getDiscount(), null);
	}
	
	public BigDecimal formatBigDecimal(BigDecimal value){
		return value.setScale(2, RoundingMode.HALF_UP);
	}
}
