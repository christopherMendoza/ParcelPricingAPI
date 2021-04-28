package com.mynt.parcelpricing.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.mynt.parcelpricing.exception.ApiRequestException;
import com.mynt.parcelpricing.model.MathEquation;

public class FileUtil {

	/**
	 * Load data from file to the Math equation list. 
	 * File is used for equation setup base on the parameters to compute cost of delivery.
	 * 
	 * @return the list of Math Equation parameter setup.
	 */
	public static List<MathEquation> loadMathEquation() {
		List<MathEquation> returnList = new ArrayList<>();

		Resource resource = new ClassPathResource("/files/MathEquationList.txt");
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));
			
			reader.lines().forEach(line->{
				System.out.println(line);
				
				String[] lineArr = line.split("#");
				
				System.out.println(lineArr[0]);
				System.out.println(lineArr[1]);
				System.out.println(lineArr[2]);
				System.out.println(lineArr[3]);
				System.out.println(lineArr[4]);
				returnList.add(new MathEquation(lineArr[0], lineArr[1], lineArr[2], lineArr[3], lineArr[4]));
			});
	 		
			display(returnList);
		} catch (IOException e) {
			throw new ApiRequestException(e.getMessage());
		}
		
		
		return returnList;
	}
	
	public static void display(List<MathEquation> returnList) {
		System.out.println("---------------------------");
		for(MathEquation eq: returnList) {
			System.out.println(eq.getEquationCode());
			System.out.println(eq.getEquationName());
			System.out.println(eq.getEquationDesc());
			System.out.println(eq.getCondition());
			System.out.println(eq.getEquation());
		}
	}
}