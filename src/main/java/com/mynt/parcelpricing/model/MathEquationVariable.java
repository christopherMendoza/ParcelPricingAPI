package com.mynt.parcelpricing.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MathEquationVariable {

	private String code;
	private BigDecimal value;
	private String desc;
}
