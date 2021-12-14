package com.globits.da.service;

import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ProvinceService {
    Page<ProvinceDto> getPage(int pageIndex, int pageSize);
    ProvinceDto saveOrUpdate(UUID id, ProvinceDto provinceDto);
    Boolean deleteById(UUID id);
    Page<ProvinceDto> searchByPage(ProvinceSearchDto provinceSearchDto);

}
