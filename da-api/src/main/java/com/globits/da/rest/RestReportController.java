package com.globits.da.rest;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.EmployeeReportDto;
import com.globits.da.dto.HighChartDto;
import com.globits.da.dto.HightChart5DisDto;
import com.globits.da.dto.HightChart5Dto;
import com.globits.da.dto.HightChartCountByDateDto;
import com.globits.da.dto.ProvinceReportDto;
import com.globits.da.dto.StatisticalDto;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.dto.search.HighChartSearchDto;
import com.globits.da.dto.search.ProvinceSearchDto;
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
@RequestMapping("api/report")
public class RestReportController {



    @Autowired
    private EmployeeReportService employeeReportService;




    
    @PostMapping("")
    public ResponseEntity<Page<EmployeeReportDto>> getReport(@RequestBody EmployeeSearchDTO dto ){
		return new ResponseEntity<Page<EmployeeReportDto>>(employeeReportService.searchByPage(dto),HttpStatus.OK) ;
    }
    
    @PostMapping("statistical")
    public ResponseEntity<Page<StatisticalDto>> getStatistical(@RequestBody EmployeeSearchDTO dto ){
		return new ResponseEntity<Page<StatisticalDto>>(employeeReportService.statiscalPage(dto),HttpStatus.OK) ;
    }
    @PostMapping("/byProvince")
    public ResponseEntity<Page<EmployeeDTO>> searchByProvince(@RequestBody ProvinceSearchDto dto ){
		return new ResponseEntity<Page<EmployeeDTO>>(employeeReportService.searchByProvince(dto),HttpStatus.OK) ;
    }
    
    @PostMapping("ProvinceReport")
    public ResponseEntity<Page<ProvinceReportDto>> getProvinceReport(@RequestBody ProvinceSearchDto dto ){
		return new ResponseEntity<Page<ProvinceReportDto>>(employeeReportService.provinceReport(dto),HttpStatus.OK) ;
    }
    @PostMapping("hightChart")
    public ResponseEntity<HighChartDto> hightChart( ){
		return new ResponseEntity<HighChartDto>(employeeReportService.highChart(),HttpStatus.OK) ;
    }
    
    @PostMapping("hightChartByProvince")
    public ResponseEntity<HighChartDto> hightChartSearch(@RequestBody ProvinceSearchDto dto ){
		return new ResponseEntity<HighChartDto>(employeeReportService.highChartSearch(dto),HttpStatus.OK) ;
    }
    @PostMapping("hightChartCountByDate")
    public ResponseEntity<List<HightChartCountByDateDto>> hightChartSearch(){
		return new ResponseEntity<List<HightChartCountByDateDto>>(employeeReportService.highChartCountByDate(),HttpStatus.OK) ;
    }
    
    @PostMapping("hightChartSearchByDate")
    public ResponseEntity<Page<HightChartCountByDateDto>> hightChartSearchByDate(@RequestBody HighChartSearchDto dto){
		return new ResponseEntity<Page<HightChartCountByDateDto>>(employeeReportService.highCharSearchByDate(dto),HttpStatus.OK) ;
    }
    
    @PostMapping("hightChart5")
    public ResponseEntity<List<HightChart5Dto>> hightChart5(){
		return new ResponseEntity<List<HightChart5Dto>>(employeeReportService.highChart5(),HttpStatus.OK) ;
    }
    
    @PostMapping("hightChart5Search")
    public ResponseEntity<List<HightChart5Dto>> hightChart5(@RequestBody ProvinceSearchDto dto){
		return new ResponseEntity<List<HightChart5Dto>>(employeeReportService.highChart5Search(dto),HttpStatus.OK) ;
    }
    
    @PostMapping("highChart5GroupByDistrict")
    public ResponseEntity<List<HightChart5DisDto>> hightChart5groupByDis(@RequestBody ProvinceSearchDto dto){
		return new ResponseEntity<List<HightChart5DisDto>>(employeeReportService.highChart5GroupByDistrict(dto),HttpStatus.OK) ;
    }
}