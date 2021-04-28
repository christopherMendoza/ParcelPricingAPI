package com.mynt.parcelpricing.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.WordUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.mynt.parcelpricing.bean.ParcelBean;
import com.mynt.parcelpricing.constant.AppConstant;
import com.mynt.parcelpricing.exception.ApiRequestException;
import com.udojava.evalex.Expression;
import com.udojava.evalex.Expression.ExpressionException;

@Component
public class FormulaEvaluator {

	public BigDecimal evaluate(String condition,ParcelBean bean) throws ApiRequestException {
		
		DecimalFormat df = new DecimalFormat(AppConstant.LONG_DECIMAL_FORMAT);
		ArrayList<String> eqList = new ArrayList<>();
		String modifiedEquation =  condition;
		
		//F.VAL[AMOUNT]*P.VAL[PH-COI, FIELD1, FIELD2]*V.Val[PH-PT]
		
		//Regular Expression for Fixed Value
		//F.Val[FIELDNAME]
		String regexF = "F.VAL\\[[a-zA-Z0-9-_]+\\]";
		
		//Regular Expression for Variable Value
		//V.Val[VARIABLE_NAME]
		String regexV = "V.VAL\\[[a-zA-Z0-9-_]+\\]";
		
		//Regular Expression for Multiples-of Value
		//M = M.Val[MUL_VALUE, 200, 1.50]
		String regexM = "M.VAL\\[[a-zA-Z0-9-_]+ *, *(([1-9]\\d*)?\\d)(\\.\\d+)? *, *(([1-9]\\d*)?\\d)(\\.\\d+)?\\]";
		
		//Regular Expression for Range Value
		//R.Val[RANGE_VALUE, DSTRATEPH]
		String regexR = "R.VAL\\[[a-zA-Z0-9-_]+ *, *[a-zA-Z0-9-_]+\\]";
		
		//Regular Expression for Periodic Value
		//P.Val[PH-COI, FIELD1, FIELD2]
		String regexP = "P.VAL\\[[a-zA-Z0-9-_]+ *, *[a-zA-Z0-9-_]+ *, *[a-zA-Z0-9-_]+\\]";
		
		Pattern pF = Pattern.compile(regexF);
		Pattern pV = Pattern.compile(regexV);
		Pattern pM = Pattern.compile(regexM);
		Pattern pR = Pattern.compile(regexR);
		Pattern pP = Pattern.compile(regexP);
		
		Matcher mF = pF.matcher(condition);
		Matcher mV = pV.matcher(condition);
		Matcher mM = pM.matcher(condition);
		Matcher mR = pR.matcher(condition);
		Matcher mP = pP.matcher(condition);
		
		while(mF.find()) {
			eqList.add(mF.group());
			modifiedEquation = modifiedEquation.replaceAll(Pattern.quote(mF.group()), df.format(getNumericFixedValue(mF.group(), bean)));
		}
		
		BigDecimal result = new BigDecimal(0);
		try{
			Expression exp = new Expression(modifiedEquation);
			result = exp.eval();
			
		}catch (ExpressionException ee) {
			throw new ApiRequestException(ee.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
	
	/**
	 * This value will be referenced from the ParcelBean bean as the source value reference
	 *	Syntax:
	 *		Value = F.VAL[&lt;columnName&gt;]
	 *
	 *	Where: 
	 *		columnName = Column name from ParcelBean bean object.
	 *
	 *	Sample Syntax:
	 *		F.VAL[SYSVALNUM1]
	 * @param eqFixed Equation to be evaluated
	 * @param bean parcel bean acts as the source data for evaluation
	 * @return Retrieved value
	 */
	public static BigDecimal getNumericFixedValue(final String eqFixed, ParcelBean bean) {
		String var = eqFixed.toUpperCase().replaceAll("F.VAL\\[", "").replaceAll("\\]", "");
		Object value =  getColumnValue(var.trim(), bean);
		BigDecimal rVal = null;
		if(value instanceof BigDecimal) {
			if(value != null) {
				rVal = (BigDecimal) value ;
			}
		}else if(value instanceof Float) {
			if(value != null) {
				rVal = new BigDecimal(((Float) value).longValue()) ;
			}
		}else if(value instanceof Double) {
			if(value != null) {
				rVal = new BigDecimal(((Double) value)) ;
			}
		}
		return (rVal == null) ? BigDecimal.ZERO : rVal ;
	}
	
	/**
	 * Get the value of the specified column from the Parcel bean object.
	 * 
	 * @param columnName Name of column for the value to be retrieved
	 * @param bean Parcel bean acts as the source data for evaluation
	 * @return The object value of the column
	 */
	private static Object getColumnValue(String columnName, ParcelBean bean){
		String camelColName = WordUtils.capitalizeFully(columnName, new char[]{'_'}).replaceAll("_", "");
		String getMethodName = "get"+camelColName;
		Object objValue = null;
		try{
			Method getMethod = ParcelBean.class.getMethod(getMethodName);
			objValue = getMethod.invoke(bean);
		} catch (NoSuchMethodException nsme) {
			String errorDesc = "Method " + getMethodName + " does not exist.";
			throw new ApiRequestException(errorDesc, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IllegalAccessException iae) {
			String errorDesc = "No permission to access the method " + getMethodName;
			throw new ApiRequestException(errorDesc, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (InvocationTargetException ite) {
			String errorDesc = "Invocation error for method " + getMethodName;
			throw new ApiRequestException(errorDesc, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return objValue;
	}
}
