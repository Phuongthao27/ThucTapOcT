package com.globits.da.service.impl;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.service.ExcelReaderService; 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExcelReaderImpl  {
    @Autowired
    private EmployeeRepository employeeRepository;
    public  String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "code", "name", "Email", "phone ","age" };
    static String SHEET = "Employee";
   // @Autowired
   // public EntityManager manager;
    //private XSSFWorkbook workbook;
  //  private XSSFSheet sheet;


    public List<Employee> readData(InputStream file) {


        try {
           Workbook workbook = new XSSFWorkbook(file);
           Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            List<Employee> employees = new ArrayList<Employee>();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                //skip header
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellInRow = currentRow.cellIterator();
                Employee employee = new Employee();

                int cellIndex = 0;
                while (cellInRow.hasNext()) {

                    Cell currentCell = cellInRow.next();
                    switch (cellIndex) {
                        case 0:
                            employee.setCode(currentCell.getStringCellValue());
                            break;
                        case 1:
                            employee.setName(currentCell.getStringCellValue());
                            break;
                        case 2:
                            employee.setEmail(currentCell.getStringCellValue());
                            break;
                        case 3:
                            employee.setPhone(currentCell.getStringCellValue());
                            break;
                        case 4:
                            employee.setAge((int) currentCell.getNumericCellValue());
                            break;
                    }
                    cellIndex++;
                }
                employees.add(employee);


            }
            workbook.close();
            return employees;
        } catch (IOException e) {

            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }



    }

    public void Import(MultipartFile file) {
        try {
            List<Employee> employees = readData(file.getInputStream());
            employeeRepository.saveAll(employees);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }


    public Boolean hasExcelFomat(MultipartFile file) {
        if(!TYPE.equals(file.getContentType())){
            return false;
        }
        return true;
    }
}
