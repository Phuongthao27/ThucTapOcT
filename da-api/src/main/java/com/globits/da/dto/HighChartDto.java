package com.globits.da.dto;

public class HighChartDto{
	private Float empHaveOne;
	private Float empHaveTwo;
	private Float empHaveMoreThan2;
	
	public HighChartDto(Object[] entity) {
		this.empHaveOne = entity[0] != null?Float.parseFloat(entity[0].toString()):null;
		this.empHaveTwo = entity[0] != null?Float.parseFloat(entity[1].toString()):null;
		this.empHaveMoreThan2 = entity[0] != null?Float.parseFloat(entity[2].toString()):null;
	}

	public Float getEmpHaveOne() {
		return empHaveOne;
	}

	public void setEmpHaveOne(Float empHaveOne) {
		this.empHaveOne = empHaveOne;
	}

	public Float getEmpHaveTwo() {
		return empHaveTwo;
	}

	public void setEmpHaveTwo(Float empHaveTwo) {
		this.empHaveTwo = empHaveTwo;
	}

	public Float getEmpHaveMoreThan2() {
		return empHaveMoreThan2;
	}

	public void setEmpHaveMoreThan2(Float empHaveMoreThan2) {
		this.empHaveMoreThan2 = empHaveMoreThan2;
	};
	
	
}