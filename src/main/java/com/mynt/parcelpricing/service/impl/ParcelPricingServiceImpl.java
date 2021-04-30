package com.mynt.parcelpricing.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mynt.parcelpricing.bean.ParcelBean;
import com.mynt.parcelpricing.bean.PaymentBean;
import com.mynt.parcelpricing.constant.AppConstant;
import com.mynt.parcelpricing.exception.ApiRequestException;
import com.mynt.parcelpricing.model.MathEquation;
import com.mynt.parcelpricing.model.VoucherItem;
import com.mynt.parcelpricing.service.ParcelPricingService;
import com.mynt.parcelpricing.util.FileUtil;
import com.mynt.parcelpricing.util.FormulaEvaluator;

import io.netty.util.internal.StringUtil;

/**
 * Service class that handles the main logic of Parcel pricing computation.
 * 
 * @author Chris
 * @since 4/27/2021
 *
 */
@Service
public class ParcelPricingServiceImpl implements ParcelPricingService{

	@Autowired
	private FormulaEvaluator formulaEvaluator;
	
	@Autowired
	private VoucherServiceImpl voucherService;
	
	public PaymentBean compute(ParcelBean bean) throws ApiRequestException {
		
		PaymentBean paymentBean = new PaymentBean();
		bean.computeVolume();
		
		List<MathEquation> equationList = FileUtil.loadMathEquation();
		for(MathEquation equation: equationList) {
			BigDecimal resultCondition = formulaEvaluator.evaluate(equation.getCondition(),bean);
			
			/**
			 * If result condition is 0 means condition did not meet
			 * then iterate with the next condition.
			 */
			if(resultCondition.compareTo(BigDecimal.ZERO) < 1) {
				continue;
			}
			
			/**
			 * Condition met but the equation is not application or rejected.
			 */
			if(equation.getEquation().equals(AppConstant.NA)) {
				throw new ApiRequestException("Transaction is rejected weight is more than the allowable limit");
			}
			
			/**
			 * Compute for the pricing base on the equation set.
			 */
			BigDecimal shippingFee = formulaEvaluator.evaluate(equation.getEquation(),bean).setScale(2,RoundingMode.HALF_UP);
			paymentBean.setShippingFee(shippingFee);
			paymentBean.setTotal(shippingFee);
			
			//Check if there is voucher code pass from the request.
			this.validateAndProcessVoucher(bean, paymentBean);
			
			break;
		}
		
		return paymentBean;
	}

	/**
	 * Validated voucher code and get the details.
	 * 
	 * @param bean - the request bean object.
	 * @param paymentBean - the payment bean object.
	 */
	private void validateAndProcessVoucher(ParcelBean bean, PaymentBean paymentBean) throws ApiRequestException {
		String voucherCode = bean.getVoucherCode();
		if(!StringUtil.isNullOrEmpty(voucherCode)) {
			VoucherItem voucherItem = voucherService.getVoucher(voucherCode);
			
			if(voucherItem == null) {
				throw new ApiRequestException("Voucher code " + voucherCode + "is invalid");			}
			
			if(voucherItem != null) {
				paymentBean.setDiscount(voucherItem.getDiscount());
			}
		}
	}
	
	public List<MathEquation> equationlist() throws ApiRequestException {
		List<MathEquation> returnList = new ArrayList<>();
		returnList = FileUtil.loadMathEquation();
		
		return returnList;
	}
}