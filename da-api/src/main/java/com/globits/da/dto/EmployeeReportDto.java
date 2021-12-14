package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
//@AllArgsConstructor

public class EmployeeReportDto  {


 
    private String employeeName;
  

    private Integer totalCer;

    public EmployeeReportDto(Object[]  entity) {
     	this.employeeName =entity[0]!= null? entity[0].toString():"";
     	this.totalCer = Integer.parseInt(entity[1].toString());
     			
    }
    
    public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String EmployeeName) {
		this.employeeName = EmployeeName;
	}

	public Integer getTotalCer() {
		return totalCer;
	}

	public void setTotalCer(Integer TotalCer) {
		TotalCer = TotalCer;
	}

	public EmployeeReportDto() {
		// TODO Auto-generated constructor stub
	}
 
  
}
