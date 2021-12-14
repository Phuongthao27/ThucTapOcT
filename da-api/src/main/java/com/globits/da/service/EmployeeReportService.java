package com.globits.da.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.EmployeeReportDto;
import com.globits.da.dto.HighChartDto;
import com.globits.da.dto.HightChart5DisDto;
import com.globits.da.dto.HightChart5Dto;
import com.globits.da.dto.HightChartCountByDateDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.ProvinceReportDto;
import com.globits.da.dto.StatisticalDto;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.dto.search.HighChartSearchDto;
import com.globits.da.dto.search.ProvinceSearchDto;



@Service
public interface EmployeeReportService {
	Page<EmployeeReportDto> searchByPage(EmployeeSearchDTO dto);
	Page<StatisticalDto> statiscalPage(EmployeeSearchDTO dto);
	Page<EmployeeDTO> searchByProvince(ProvinceSearchDto dto);
	Page<ProvinceReportDto> provinceReport(ProvinceSearchDto dto);
	HighChartDto highChart();
	HighChartDto highChartSearch(ProvinceSearchDto dto);
	List<HightChartCountByDateDto> highChartCountByDate();
	Page<HightChartCountByDateDto> highCharSearchByDate(HighChartSearchDto dto);
	List<HightChart5Dto> highChart5();
	List<HightChart5Dto> highChart5Search(ProvinceSearchDto dto);
	List<HightChart5DisDto> highChart5GroupByDistrict(ProvinceSearchDto dto);
	
}
