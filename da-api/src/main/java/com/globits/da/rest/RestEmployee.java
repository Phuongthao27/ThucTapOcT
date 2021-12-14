package com.globits.da.rest;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.EmployeeReportDto;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.service.*;
import com.globits.da.service.impl.ExcelReaderImpl;
import com.globits.da.service.impl.ExcelServiceImpl;
import com.globits.da.service.impl.ExcelWriterIPM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/employee")
public class RestEmployee {

    @Autowired
    private ExcelReaderService excelReaderService;

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private EmployeeReportService employeeReportService;

    @Autowired
    private ExcelWriterService excelWriterService;

    @GetMapping("page/{pageindex}/{pagesize}")
    public Page<EmployeeDTO> getPage(@PathVariable int pageindex,@PathVariable int pagesize) {
        return employeeService.getPage(pageindex,pagesize);


    }

    @GetMapping("Employee/{name}")
    public List<EmployeeSearchDTO> searchEmployeeDTOByName(@PathVariable String name) {
        return employeeService.searchDTO(name);
    }

    @PostMapping("result")
    public Page<EmployeeDTO> searchByPage(@RequestBody EmployeeSearchDTO employeeSearchDTO) {
        return employeeService.searchByPage(employeeSearchDTO);
    }


    @PostMapping("")
    public ResponseEntity<EmployeeDTO> saveOrUpdateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO ) {

        return new ResponseEntity < >(employeeService.saveOrUpdate(null,employeeDTO),HttpStatus.OK);
    }

    @PostMapping("id/{id}")
    public ResponseEntity<EmployeeDTO> saveOrUpdateEmployee(@PathVariable UUID id,@RequestBody EmployeeDTO employeeDTO ) {

        return new ResponseEntity < >(employeeService.saveOrUpdate(id,employeeDTO),HttpStatus.OK);
    }

    @DeleteMapping("Employee/{id}")

    public void deleteById(@PathVariable UUID id) {
        employeeService.deleteByID(id);
    }


    @GetMapping("/excel")
    public void exportExcel(HttpServletResponse response,@RequestBody EmployeeSearchDTO employeeSearchDTO) throws Exception {
        List<EmployeeDTO> list = excelWriterService.getList(employeeSearchDTO);
        ExcelWriterIPM excelWriter = new ExcelWriterIPM(list);
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=test.xlsx";
        response.setHeader(headerKey,headerValue);
        try {

            excelWriter.Export(response);

        } catch (Exception e) {
            e.printStackTrace();

        }



//        response.setContentType("application/octet-stream");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=test.xlsx";
//        response.setHeader(headerKey, headerValue);
//        ExcelWriterIPM exportEx = new ExcelWriterIPM();
//        exportEx.exportExcel(response);


    }

   // @PostMapping("/excelReader")
//    public void uploadFile(@RequestParam("file") MultipartFile file){
//        if(excelReaderService.hasExcelFomat(file)){
//            try{
//                excelReaderService.Import(file);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//
//    }
    @PostMapping("/excelReader")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (excelReaderService.hasExcelFomat(file)) {
            try {
                excelReaderService.Import(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }
    
    @PostMapping("report/{pageIndex}/{pageSize}")
    public ResponseEntity<Page<EmployeeDTO>> getReport(@PathVariable int pageIndex, @PathVariable int pageSize ){
		return new ResponseEntity<Page<EmployeeDTO>>(employeeService.report(pageIndex,pageSize),HttpStatus.OK) ;
    }

}