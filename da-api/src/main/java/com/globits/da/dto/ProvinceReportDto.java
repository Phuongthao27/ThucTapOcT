package com.globits.da.dto;

public class ProvinceReportDto{
	private String districtName;
	private Integer totalEmployee;
	
	public ProvinceReportDto(Object[] entity) {
		this.districtName = entity[0].toString();
		this.totalEmployee = Integer.parseInt(entity[1].toString());
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public Integer getTotalEmployee() {
		return totalEmployee;
	}

	public void setTotalEmployee(Integer totalEmployee) {
		this.totalEmployee = totalEmployee;
	}
	
	
	
}