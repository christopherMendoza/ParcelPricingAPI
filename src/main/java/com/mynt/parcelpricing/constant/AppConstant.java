package com.mynt.parcelpricing.constant;

import java.math.RoundingMode;

/**
 * Application Constant being used within the parcel API.
 * 
 * @author Cristopher Mendoza
 * @since 4/27/2021
 */
public class AppConstant {

	/**
	 * UTF8 BOM Header value
	 */
	public static final String UTF8_BOM = "\uFEFF";
	
	/**
	 * Rounding Method
	 */
	public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
	
	/**
	 * Standard Three Decimal Format
	 */
	public static final String STANDARD_DECIMAL_FORMAT = "#0.000";
	
	/**
	 * Standard Two Decimal Format
	 */
	public static final String TWO_DECIMAL_FORMAT = "#0.00";
	
	/**
	 * Standard Comma based decimal format
	 */
	public static final String STANDARD_COMMA_DECIMAL_FORMAT = "#,##0.00";
	
	/**
	 * Standard Comma based whole number format
	 */
	public static final String STANDARD_COMMA_WHOLE_FORMAT = "#,##0";
	
	/**
	 * Standard long decimal format
	 */
	public static final String LONG_DECIMAL_FORMAT = "#0.0000000000";
	
	/**
	 * Not Applicable/Rejected
	 */
	public static final String NA = "N/A";
}
