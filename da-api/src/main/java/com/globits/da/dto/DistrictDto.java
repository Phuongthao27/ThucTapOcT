package com.globits.da.dto;

import com.globits.core.domain.BaseObject;
import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class DistrictDto extends BaseObjectDto {
    private String code;
    private String name;
    private ProvinceDto provinceDto;
    private Set<CommuneDto> communeDtos;
public DistrictDto() {
	// TODO Auto-generated constructor stub
}
    public DistrictDto(District district,Boolean check) {
        if(district != null) {
            this.setId(district.getId());
            this.code = district.getCode();
            this.name = district.getName();
            if(check) {
                this.provinceDto = new ProvinceDto(district.getProvince());
            }
            this.communeDtos = new HashSet<CommuneDto>();
            if (district.getCommunes() != null && district.getCommunes().size() > 0) {
                for (Commune commune : district.getCommunes()) {
                    this.communeDtos.add(new CommuneDto(commune,false));
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

    public ProvinceDto getProvinceDto() {
        return provinceDto;
    }

    public void setProvinceDto(ProvinceDto provinceDto) {
        this.provinceDto = provinceDto;
    }

    public Set<CommuneDto> getCommuneDtos() {
        return communeDtos;
    }

    public void setCommuneDtos(Set<CommuneDto> communeDtos) {
        this.communeDtos = communeDtos;
    }
}
