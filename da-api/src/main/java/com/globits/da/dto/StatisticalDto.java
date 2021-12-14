package com.globits.da.dto;

public class StatisticalDto{
	private String provinceName;
	private Integer total; 
	
	public StatisticalDto(Object[] entity){
		this.provinceName = entity[0] != null? entity[0].toString():"";
		this.total = Integer.parseInt(entity[1].toString());
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
}