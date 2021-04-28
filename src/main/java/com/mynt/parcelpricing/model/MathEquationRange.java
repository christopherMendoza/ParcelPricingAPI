package com.mynt.parcelpricing.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MathEquationRange {

	private String rangeCode;
	private String rangeName;
	private BigDecimal minValue;
	private BigDecimal maxValue;
	private BigDecimal value;
}
