package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class ProvinceDto extends BaseObjectDto {

    private String code;
    private String name;
	private Set<DistrictDto> districtDto;

public ProvinceDto() {
	// TODO Auto-generated constructor stub
}
    public ProvinceDto(Province entity){
        if(entity != null) {
        	this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.districtDto = new HashSet<DistrictDto>();
            if(entity.getDistricts() != null && entity.getDistricts().size()>0){
            	for(District district : entity.getDistricts()){
            		this.districtDto.add(new DistrictDto(district,false));
				}
			}
        }
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


	public Set<DistrictDto> getDistrictDto() {
		return districtDto;
	}


	public void setDistrictDto(Set<DistrictDto> districtDto) {
		this.districtDto = districtDto;
	}




}
