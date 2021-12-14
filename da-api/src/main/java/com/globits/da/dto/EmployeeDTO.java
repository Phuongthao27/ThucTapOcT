package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
//@AllArgsConstructor

public class EmployeeDTO extends BaseObjectDto{


  //  @NotNull(message = "Code can not be null")
    @NotBlank(message = "Code can not be null or has blankSpace")
    @Size(min = 6,max = 10, message = "charactor can't more than 10 and less than 6")
    private String code;
    @NotNull(message = "Name can't be null!")
    private String name;
    @Email(message = "Email Invalidate1!")
    private String email;
    @Size(min = 11,max = 11,message = "Phone number can't be more than 11 charactor")
    private String phone;
    @Positive(message = "Age can not be Negative or zero!")
    private Integer age;
    
    private ProvinceDto provinceDto;
    private DistrictDto districtDto;
    private CommuneDto communeDto;
    

    private String ErroMessage;

    public EmployeeDTO(Employee entity,Boolean check) {
        //if(entity !=null){
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.email = entity.getEmail();
            this.phone = entity.getPhone();
            this.age = entity.getAge();
            if(check) {
            	this.provinceDto = new ProvinceDto(entity.getProvince());
            	this.districtDto = new DistrictDto(entity.getDistrict(),false);
            	this.communeDto = new CommuneDto(entity.getCommune(),false);
            }

       // }
		this.ErroMessage=null;

    }
    
    public EmployeeDTO(Object[] entity) {
            
            this.code = entity[0].toString();
            this.name = entity[1].toString();
            this.email = entity[2].toString();
            this.phone = entity[3].toString();
            this.age = Integer.parseInt(entity[4].toString());
            

    }
    
    public EmployeeDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getErroMessage() {
		return ErroMessage;
	}

	public void setErroMessage(String erroMessage) {
		ErroMessage = erroMessage;
	}
	public ProvinceDto getProvinceDto() {
		return provinceDto;
	}
	public void setProvinceDto(ProvinceDto provinceDto) {
		this.provinceDto = provinceDto;
	}
	public DistrictDto getDistrictDto() {
		return districtDto;
	}
	public void setDistrictDto(DistrictDto districtDto) {
		this.districtDto = districtDto;
	}
	public CommuneDto getCommuneDto() {
		return communeDto;
	}
	public void setCommuneDto(CommuneDto commune) {
		this.communeDto = commune;
	}
}
