package com.globits.da.service.impl;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.service.ExcelWriterService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
@Service
public class ExcelWriterIPM implements ExcelWriterService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    public EntityManager manager;
    private static List<EmployeeDTO> list;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    public ExcelWriterIPM(List<EmployeeDTO> employeeList){
        this.list = employeeList;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Employee");

    }



    private void writeHeaderRow(){
        Row row = sheet.createRow(0);

        Cell cell1 = row.createCell(1);
        cell1.setCellValue("Code");

        Cell cell2 = row.createCell(2);
        cell2.setCellValue("Name");

        Cell cell3 = row.createCell(3);
        cell3.setCellValue("email");

        Cell cell4 = row.createCell(4);
        cell4.setCellValue("phone");

        Cell cell5 = row.createCell(5);
        cell5.setCellValue("age");


    }
    private void writeDatarRow(){
        int numRow = 1;

       for(EmployeeDTO ep : list){
           Row row = sheet.createRow(numRow++);
           row.createCell(0).setCellValue(ep.getId().toString());
           row.createCell(1).setCellValue(ep.getCode());
           row.createCell(2).setCellValue(ep.getName());
           row.createCell(3).setCellValue(ep.getEmail());
           row.createCell(4).setCellValue(ep.getPhone());
           row.createCell(5).setCellValue(ep.getAge());


       }

    }


    @Override
    public void Export(HttpServletResponse response) {
        try {
            writeHeaderRow();
            writeDatarRow();
            // Write the output to a file
            ServletOutputStream outputStream =response.getOutputStream();
            workbook.write(outputStream);
            // Closing the workbook
            workbook.close();
            //
            outputStream.close();
        }catch (IOException e){

        }
    }

    @Override
    public void Import(InputStream file) {
        try {
            workbook = new XSSFWorkbook(file);
            sheet = workbook.getSheet("EmployeeFile");
            Iterator<Row> rows = sheet.iterator();
            List<Employee> employees = new ArrayList<Employee>();
            int rowNumber = 0;
            while (rows.hasNext()){
                Row currentRow = rows.next();
                //skip header
                if(rowNumber == 0){
                    rowNumber ++;
                    continue;
                }
               Iterator <Cell> cellInRow = currentRow.cellIterator();

                Employee employee = new Employee();
                int cellIndex = 0;
                while (cellInRow.hasNext()){
                    Cell currentCell = cellInRow.next();
                    switch (cellIndex){
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
                            employee.setAge((int)currentCell.getNumericCellValue());
                            break;
                    }
                    cellIndex++;
                }
                employees.add(employee);
                employeeRepository.saveAll(employees);

            }
            workbook.close();

        }catch (IOException e){
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        };
    }

    @Override
    public List<EmployeeDTO> getList(EmployeeSearchDTO dto) {
        if(dto == null){
            return null;
        }


        String whereClause="";
        // String orderBy = "ORDER BY entity.cre"
        String orderBy = " ORDER BY entity.createDate DESC";

        String sql = "select new com.globits.da.dto.EmployeeDTO(entity) from  Employee as entity where (1=1)  ";

        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
            whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
        }
        sql += whereClause + orderBy;
        Query q = manager.createQuery(sql, EmployeeDTO.class);

        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
            q.setParameter("text",'%'+dto.getKeyword()+'%');

        }

        List<EmployeeDTO> entities = q.getResultList();
       return entities;
    }
























    //  public void exportExcel(HttpServletResponse response)throws Exception{
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        XSSFSheet sheet = workbook.createSheet("Sheet Test");
//        Row row = sheet.createRow(0);
//        Cell cell = row.createCell(0);
//        cell.setCellValue("Cell 1");
//        cell = row.createCell(1);
//        cell.setCellValue("Cell 2");
//        ServletOutputStream outputStream = response.getOutputStream();
//        workbook.write(outputStream);
//        workbook.close();
//        outputStream.close();
//  }

}


