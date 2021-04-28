package com.mynt.parcelpricing.bean;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Parcel Bean container class.
 * 
 * @author Chris
 * @since 4/27/2021
 */

@Getter
@Setter
@NoArgsConstructor
public class ParcelBean extends BaseBean{

	@Min(value = 1, message = "Height must have a value  more than 0.")
	private int height;
	
	@Min(value = 1, message = "Weight must have a value  more than 0.")
	private double weight;
	
	@Min(value = 1, message = "Width must have a value  more than 0.")
	private double width;
	
	@Min(value = 1, message = "Length must have a value  more than 0.")
	private double length;
	
	private double volume;
	
	private String voucherCode;
	
	
	
	public void computeVolume() {
		double volume = height * width * length;
		setVolume(volume);
	}

	public ParcelBean(int height, double weight,double width,double length, String voucherCode) {
		super();
		this.height = height;
		this.weight = weight;
		this.width = width;
		this.length = length;
		this.voucherCode = voucherCode;
	}
	
	public ParcelBean(int height, double weight,double width,double length) {
		super();
		this.height = height;
		this.weight = weight;
		this.width = width;
		this.length = length;
	}
}