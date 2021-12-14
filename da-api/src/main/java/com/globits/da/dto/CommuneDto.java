package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Commune;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class CommuneDto extends BaseObjectDto {
    private String code;
    private String name;
    private DistrictDto districtDto;
public CommuneDto() {
	// TODO Auto-generated constructor stub
}
    public CommuneDto(Commune entity,Boolean check) {
        this.code = entity.getCode();
        this.name = entity.getName();
        if(check) {
            this.districtDto = new DistrictDto(entity.getDistrict(),false);
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

    public DistrictDto getDistrictDto() {
        return districtDto;
    }

    public void setDistrictDto(DistrictDto districtDto) {
        this.districtDto = districtDto;
    }
}
