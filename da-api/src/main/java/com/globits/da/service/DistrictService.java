package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.District;
import com.globits.da.domain.Employee;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.DistrictSearchDto;
import com.globits.da.dto.search.EmployeeSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public interface DistrictService extends GenericService<District,UUID > {
    Page<DistrictDto> getPage(int pageindex, int pagesize);
    DistrictDto saveOrUpdate(UUID id, DistrictDto dto);
    Boolean deleteByID (UUID id);
    Page<DistrictDto> searchByPage(DistrictSearchDto dto);
}
