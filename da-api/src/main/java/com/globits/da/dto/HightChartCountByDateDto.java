package com.globits.da.dto;

public class HightChartCountByDateDto{
	private String date;
	private Integer totalEmpl;
	
	public HightChartCountByDateDto(Object[] entity) {
		this.date = entity[0] != null ? entity[0].toString() : "";
		this.totalEmpl = entity[1] != null ? Integer.parseInt(entity[1].toString()) : null;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getTotalEmpl() {
		return totalEmpl;
	}
	public void setTotalEmpl(Integer totalEmpl) {
		this.totalEmpl = totalEmpl;
	}
	
	
}