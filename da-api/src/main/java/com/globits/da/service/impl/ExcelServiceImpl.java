package com.globits.da.service.impl;

import com.globits.da.domain.Employee;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.service.ExcelHelper;
import com.globits.da.service.ExcelReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class ExcelServiceImpl implements ExcelReaderService {
    @Autowired
    private EmployeeRepository employeeRepository;


    public void Import(MultipartFile file) {

        try {
            List<Employee> employees = ExcelHelper.readFile(file.getInputStream());
            employeeRepository.saveAll(employees);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    @Override
    public Boolean hasExcelFomat(MultipartFile file) {
        if (!ExcelHelper.TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }
}
