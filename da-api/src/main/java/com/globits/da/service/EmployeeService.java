package com.globits.da.service;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    Page<EmployeeDTO> getPage(int pagesize, int pageindex);
    List<Employee> findAll();
    List<EmployeeSearchDTO> searchDTO(String name);
    EmployeeDTO saveOrUpdate(UUID id, EmployeeDTO dto);
    Boolean deleteByID (UUID id);
    Page<EmployeeDTO> searchByPage(EmployeeSearchDTO dto);
    Page<EmployeeDTO> report(int pageIndex,int pageSize);

}
