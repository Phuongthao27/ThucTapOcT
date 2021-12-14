package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Certificate;
import com.globits.da.domain.District;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.mail.Message;
import java.util.Date;

@Getter
@Setter

public class CertificateDto extends BaseObjectDto {
    private  String name;
    private Date date;
    private ProvinceDto province;
    private EmployeeDTO employee;
    public CertificateDto(Certificate entity){
        this.name=entity.getName();
        this.date = entity.getDate();
        this.province = new ProvinceDto(entity.getProvince());
        this.employee = new EmployeeDTO(entity.getEmployee(),false);

    }
    public CertificateDto() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public ProvinceDto getProvince() {
		return province;
	}
	public void setProvince(ProvinceDto province) {
		this.province = province;
	}
	public EmployeeDTO getEmployee() {
		return employee;
	}
	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}
    
    

}
