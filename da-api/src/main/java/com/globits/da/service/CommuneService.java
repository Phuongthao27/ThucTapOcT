package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Category;
import com.globits.da.domain.Commune;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.CommuneSearchDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public interface CommuneService  extends GenericService<Commune, UUID> {
    Page<CommuneDto> getPage(int pageIndex, int pageSize);
    CommuneDto saveOrUpdate(UUID id, CommuneDto communeDto);
    Boolean deleteById(UUID id);
    Page<CommuneDto> searchByPage(CommuneSearchDto communeSearchDto);
}
