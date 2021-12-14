package com.globits.da.dto;

public class HightChart5DisDto{
	private String DistrictName;
	private Integer equal1;
	private Integer equal2;
	private Integer moreThan2;
	
	public HightChart5DisDto(Object[] entity) {
		this.DistrictName = entity[0] != null ? entity[0].toString() : "";
		this.equal1 = entity[1] != null ? Integer.parseInt(entity[1].toString()):null;
		this.equal2 = entity[2] != null ? Integer.parseInt(entity[2].toString()):null;
		this.moreThan2 = entity[3] != null ? Integer.parseInt(entity[3].toString()):null;
	}
	
	public String getProvinceName() {
		return DistrictName;
	}
	public void setProvinceName(String districtName) {
		this.DistrictName = districtName;
	}
	public Integer getEqual1() {
		return equal1;
	}
	public void setEqual1(Integer equal1) {
		this.equal1 = equal1;
	}
	public Integer getEqual2() {
		return equal2;
	}
	public void setEqual2(Integer equal2) {
		this.equal2 = equal2;
	}
	public Integer getMoreThan2() {
		return moreThan2;
	}
	public void setMoreThan2(Integer moreThan2) {
		this.moreThan2 = moreThan2;
	}
	
	
	
}