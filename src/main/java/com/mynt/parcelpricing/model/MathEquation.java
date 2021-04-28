package com.mynt.parcelpricing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MathEquation {

	private String equationCode;
	private String equationName;
	private String condition;
	private String equation;
	private String equationDesc;
}
