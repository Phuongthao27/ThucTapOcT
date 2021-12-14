package com.globits.da.service;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
@Service
public interface ExcelWriterService {
    void Export(HttpServletResponse response);
    void Import(InputStream file);
    List <EmployeeDTO> getList(EmployeeSearchDTO employeeSearchDTO);

}
