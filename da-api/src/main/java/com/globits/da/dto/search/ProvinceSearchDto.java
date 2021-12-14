package com.globits.da.dto.search;

import com.globits.da.domain.Province;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Column;
@Getter
@Setter
public class ProvinceSearchDto {
    private String code;
    private String name;

    private int pageIndex;
    private int pageSize;
    private String keyword;

	public ProvinceSearchDto() {
	}

	public ProvinceSearchDto(Province entity){
        if(entity != null) {
            this.code = entity.getCode();
            this.name = entity.getName();
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

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}


}
