package com.mynt.parcelpricing.bean;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentBean extends BaseBean{

	private BigDecimal shippingFee;
	private BigDecimal discount;
	private BigDecimal total;
	
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
		
		if(discount.compareTo(BigDecimal.ZERO) > 0) {
			setTotal(this.shippingFee.subtract(this.discount));
		}
	}
}
