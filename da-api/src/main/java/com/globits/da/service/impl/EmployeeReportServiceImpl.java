package com.globits.da.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
import com.globits.da.service.EmployeeReportService;
@Service
public class EmployeeReportServiceImpl implements EmployeeReportService {
	@Autowired
	private EntityManager entityManager;

	@Override
	public Page<EmployeeReportDto> searchByPage(EmployeeSearchDTO dto) {
		
		 if(dto == null){
	            return null;
	        }
	        int pageIndex = dto.getPageIndex();
	        int pageSize = dto.getPageSize();

	        if( pageIndex > 0){
	            pageIndex--;
	        }else {
	            pageIndex = 0;
	        }

	       // String whereClause="";
	       // String orderBy = "ORDER BY entity.cre"
	       // String orderBy = " ORDER BY entity.createDate DESC";
	        String sqlCount = "select count(id) from (SELECT e.id FROM employee as e "
	        		+ "JOIN tbl_certificate as c on e.id = c.employee_id"
	        		+ " GROUP BY c.employee_id HAVING COUNT(c.employee_id) > 1)as T ";
	        
	        String sql = "SELECT e.name ,count(c.name) FROM employee"
	        		+ " as e JOIN tbl_certificate as c on e.id = c.employee_id"
	        		+ "  GROUP BY c.employee_id HAVING COUNT(c.employee_id) > 1";
	        		
//
//	        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
//	            whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
//	        }
//	        sql += whereClause;
//	        sqlCount += whereClause;


	        Query q = entityManager.createNativeQuery(sql);
	        Query qCount = entityManager.createNativeQuery(sqlCount);

//	        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
//	            q.setParameter("text",'%'+dto.getKeyword()+'%');
//	            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
//	        }

	        int startPosition = pageIndex * pageSize;
	        q.setFirstResult(startPosition);
	        q.setMaxResults(pageSize);
	        List<EmployeeReportDto> entities = new ArrayList<>();
	        List<Object[]> resultSql = q.getResultList();
	        
	        for (Object[] objects : resultSql) {
	        	EmployeeReportDto entity = new EmployeeReportDto(objects);
	        	entities.add(entity);
			}
	        BigInteger countAc = (BigInteger) qCount.getSingleResult();
	        Long  count = countAc.longValue();
	        Pageable pageable = PageRequest.of(pageIndex, pageSize);
	        Page<EmployeeReportDto> result = new PageImpl<EmployeeReportDto>(entities, pageable, count);
	        

	        return result;
	}

	@Override
	public Page<StatisticalDto> statiscalPage(EmployeeSearchDTO dto) {
		if(dto == null) {
			return null;
		}
		int pageIndex = dto.getPageIndex();
	    int pageSize = dto.getPageSize();

	    if( pageIndex > 0){
	        pageIndex--;
	    }else {
	        pageIndex = 0;
	    }
		
	    String sqlCount = "select count(*) from (select p.name,COUNT(e.name) from employee as e , province as p "
	    		+ "where e.province_id = p.id  GROUP BY province_id ) as T";
	   
	    String sql = "select p.name,COUNT(e.name) from employee as e , province as p "
	    		+ "where e.province_id = p.id"
	    		+ " GROUP BY province_id";
	    
	      Query q = entityManager.createNativeQuery(sql);
	      Query qCount = entityManager.createNativeQuery(sqlCount);
	      
	      int startPosition = pageIndex * pageSize;
	      q.setFirstResult(startPosition);
	      q.setMaxResults(pageSize);
	      
	      List<StatisticalDto> statisticalDtos = new ArrayList<>();
	      @SuppressWarnings("unchecked")
	      List<Object[]> resultSql =(List<Object[]>) q.getResultList();
	  
	      for (Object[] objects : resultSql) {
	    	  StatisticalDto entity = new StatisticalDto(objects);
	    	  statisticalDtos.add(entity);
	      }
	      
	      //BigInteger resultCount = (BigInteger) qCount.getSingleResult();
	      Object a = qCount.getSingleResult();
	      
	      Long count = Long.parseLong(a.toString());
	      
	      Pageable pageable = PageRequest.of(pageIndex, pageSize);
	      Page<StatisticalDto> result = new PageImpl<StatisticalDto>(statisticalDtos, pageable, count);
		return result;
	}

	@Override
	public Page<EmployeeDTO> searchByProvince(ProvinceSearchDto dto) {
		if(dto == null ) {
			return null;
		}
		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();
		
		if(pageIndex > 0) {
			pageIndex--;
		}else {
			pageIndex = 0;
		}
		
		String sqlCount = "select count(*) from"
				+ " (select e.code,e.name,e.email,e.phone,e.age from employee as e left join province as p on e.province_id = p.id WHERE p.id = :name or p.name = :name ) "
				+ "as T";
		String sql = "select e.code,e.name,e.email,e.phone,e.age from employee as e left join province as p on e.province_id = p.id WHERE e.province_id or p.name = :text  ";
		
		Query q = entityManager.createNativeQuery(sql);
		q.setParameter("text", dto.getKeyword());
		Query qCount = entityManager.createNativeQuery(sqlCount);
		qCount.setParameter("name", dto.getKeyword());
		
		 int startPosition = pageIndex * pageSize;
	      q.setFirstResult(startPosition);
	      q.setMaxResults(pageSize);
	      
	      List<EmployeeDTO> employeeDTOs = new ArrayList<>();
	      @SuppressWarnings("unchecked")
	      List<Object[]> resultSql =(List<Object[]>) q.getResultList();
	  
	      for (Object[] objects : resultSql) {
	    	  EmployeeDTO entity = new EmployeeDTO(objects);
	    	  employeeDTOs.add(entity);
	      }
	      Object a = qCount.getSingleResult();
	      
	      Long count = Long.parseLong(a.toString());
	      
	      Pageable pageable = PageRequest.of(pageIndex, pageSize);
	      Page<EmployeeDTO> result = new PageImpl<EmployeeDTO>(employeeDTOs, pageable, count);
		return result;
	}

	@Override
	public Page<ProvinceReportDto> provinceReport(ProvinceSearchDto dto) {
		if(dto == null ) {
			return null;
		}
		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();
		
		if(pageIndex > 0) {
			pageIndex--;
		}else {
			pageIndex = 0;
		}
		
		String sqlCount = "select count(*) from "
				+ "(select d.name from employee as e, district as d , province as p "
				+ "WHERE e.district_id = d.id and d.province_id = p.id and p.name = :text "
				+ "GROUP BY e.district_id) as T";
		String sql = "select d.name,count(*) from employee as e, district as d , province as p "
				+ "WHERE e.district_id = d.id and d.province_id = p.id and p.name = :text "
				+ "GROUP BY e.district_id  ";
		
		Query q = entityManager.createNativeQuery(sql);
		q.setParameter("text", dto.getKeyword());
		Query qCount = entityManager.createNativeQuery(sqlCount);
		qCount.setParameter("text", dto.getKeyword());
		
		 int startPosition = pageIndex * pageSize;
	      q.setFirstResult(startPosition);
	      q.setMaxResults(pageSize);
	      
	      List<ProvinceReportDto> provinceReportDtos = new ArrayList<>();
	      @SuppressWarnings("unchecked")
	      List<Object[]> resultSql =(List<Object[]>) q.getResultList();
	  
	      for (Object[] objects : resultSql) {
	    	  ProvinceReportDto entity = new ProvinceReportDto(objects);
	    	  provinceReportDtos.add(entity);
	      }
	      Object a = qCount.getSingleResult();
	      
	      Long count = Long.parseLong(a.toString());
	      
	      Pageable pageable = PageRequest.of(pageIndex, pageSize);
	      Page<ProvinceReportDto> result = new PageImpl<ProvinceReportDto>(provinceReportDtos, pageable, count);
		return result;
	}

	@Override
	public HighChartDto highChart() {
		
		String sql = "CALL procedurReport3";
		
		Query q = entityManager.createNativeQuery(sql);
	    
	      @SuppressWarnings("unchecked")
	      Object[] resultSql = (Object[]) q.getSingleResult();
	  
	      
	      HighChartDto highChartDto = new HighChartDto(resultSql);
	    	  
	      
		return highChartDto;
	}

	@Override
	public HighChartDto highChartSearch(ProvinceSearchDto dto) {
		if (dto == null) {
			return null;
		}
		
		String sql = "CALL procedurSearchReport3 (:text)";
		Query q = entityManager.createNativeQuery(sql);
		q.setParameter("text", dto.getKeyword());
		
		@SuppressWarnings("unchecked")
	    Object[] resultSql = (Object[]) q.getSingleResult();
	  
	    HighChartDto highChartDto = new HighChartDto(resultSql);
		return highChartDto;
	}

	@Override
	public List<HightChartCountByDateDto> highChartCountByDate() {
		String sql = "SELECT e.create_date,count(*) from employee as e "
				+ "GROUP BY DATE(e.create_date) DESC LIMIT 30";
		
		Query q = entityManager.createNativeQuery(sql);
	    
	    @SuppressWarnings("unchecked")
	   List<Object[]> resultSql = (List<Object[]>) q.getResultList();
	 	      
	   List<HightChartCountByDateDto> hightChartCountByDateDto = new ArrayList<>();
	   
	   for(Object[] objects : resultSql ) {
		   HightChartCountByDateDto var = new HightChartCountByDateDto(objects);
		   hightChartCountByDateDto.add(var);
	   }
		return hightChartCountByDateDto;
	}

	@Override
	public Page<HightChartCountByDateDto> highCharSearchByDate(HighChartSearchDto dto) {
		if(dto == null ) {
			return null;
		}
		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();
		
		if(pageIndex > 0) {
			pageIndex--;
		}else {
			pageIndex = 0;
		}
		
		
		String sqlCount = "SELECT count(*) from (SELECT e.create_date from employee as e "
				+ "WHERE   DATE(e.create_date) < :dateto AND DATE(e.create_date) > :datefrom "
				+ "GROUP BY DATE(e.create_date) )as T";
		
		String sql = "SELECT e.create_date,count(*) from employee as e"
				+ " WHERE :dateto > DATE(e.create_date) AND DATE(e.create_date) > :datefrom "
				+ "GROUP BY DATE(e.create_date) ";
		
		Query q = entityManager.createNativeQuery(sql);
		q.setParameter("datefrom", dto.getDateFrom());
		q.setParameter("dateto",dto.getDateTo());
		
		Query qCount = entityManager.createNativeQuery(sqlCount);
		qCount.setParameter("datefrom", dto.getDateFrom());
		qCount.setParameter("dateto",dto.getDateTo());
		
		 int startPosition = pageIndex * pageSize;
	      q.setFirstResult(startPosition);
	      q.setMaxResults(pageSize);
	      
	      @SuppressWarnings("unchecked")
		   List<Object[]> resultSql = (List<Object[]>) q.getResultList();
		 	      
		   List<HightChartCountByDateDto> hightChartCountByDateDto = new ArrayList<>();
		   
		   for(Object[] objects : resultSql ) {
			   HightChartCountByDateDto var = new HightChartCountByDateDto(objects);
			   hightChartCountByDateDto.add(var);
		   }
	      Object a = qCount.getSingleResult();
	      
	      Long count = Long.parseLong(a.toString());
	      
	      Pageable pageable = PageRequest.of(pageIndex, pageSize);
	      Page<HightChartCountByDateDto> result = new PageImpl<HightChartCountByDateDto>(hightChartCountByDateDto, pageable, count);
		return result;
	}

	@Override
	public List<HightChart5Dto> highChart5() {
	
		String sql = "select pr.name,COALESCE((SELECT count(*)"
				+ " from tbl_certificate ce join employee emp on emp.id=ce.employee_id "
				+ "join province pro on pro.id = emp.province_id WHERE emp.province_id=pr.id"
				+ " having count(*)=1 ),0) bang1,COALESCE((SELECT count(*) "
				+ "from tbl_certificate ce join employee emp on emp.id=ce.employee_id join "
				+ "province pro on pro.id = emp.province_id "
				+ "WHERE emp.province_id=pr.id having count(*)=2 ),0) bang2,"
				+ "COALESCE((SELECT count(*) from tbl_certificate ce join employee emp on emp.id=ce.employee_id "
				+ "join province pro on pro.id = emp.province_id WHERE emp.province_id=pr.id having count(*)>2 ),0) lonhon2 from province as pr ";
		
		Query q = entityManager.createNativeQuery(sql);
	      
	    @SuppressWarnings("unchecked")
		List<Object[]> resultSql = (List<Object[]>) q.getResultList();
		 	      
		List<HightChart5Dto> hightChart5Dtos = new ArrayList<>();
		   
		   for(Object[] objects : resultSql ) {
			   HightChart5Dto var = new HightChart5Dto(objects);
			   hightChart5Dtos.add(var);
		   }
	      
	
		return hightChart5Dtos;
	}

	@Override
	public List<HightChart5Dto> highChart5Search(ProvinceSearchDto dto) {
		if(dto == null) {
			return null;
		}
		
		String sql = "select pr.name,COALESCE((SELECT count(*) "
				+ "from tbl_certificate ce join employee emp on "
				+ "emp.id=ce.employee_id join province pro on pro.id = emp.province_id "
				+ "WHERE emp.province_id=pr.id having count(*)=1 ),0) bang1,"
				+ "COALESCE((SELECT count(*) from tbl_certificate ce join employee emp on emp.id=ce.employee_id "
				+ "join province pro on pro.id = emp.province_id WHERE emp.province_id=pr.id having count(*)=2 ),0) bang2,"
				+ "COALESCE((SELECT count(*) from tbl_certificate ce join employee emp on emp.id=ce.employee_id join "
				+ "province pro on pro.id = emp.province_id WHERE emp.province_id=pr.id having count(*)>2 ),0) lonhon2 "
				+ "from province as pr  WHERE pr.name = :text";
		
		Query q = entityManager.createNativeQuery(sql);
		q.setParameter("text", dto.getKeyword());
		
	      
	    @SuppressWarnings("unchecked")
		List<Object[]> resultSql = (List<Object[]>) q.getResultList();
		 	      
		List<HightChart5Dto> hightChart5Dtos = new ArrayList<>();
		   
		for(Object[] objects : resultSql ) {
			   HightChart5Dto var = new HightChart5Dto(objects);
			   hightChart5Dtos.add(var);
		   }
	    
	      
	     
		return hightChart5Dtos;
		
	}

	@Override
	public List<HightChart5DisDto> highChart5GroupByDistrict(ProvinceSearchDto dto) {
		if(dto == null) {
			return null;
		}
		
		String sql = "select d.name ,COALESCE((SELECT count(*)  from tbl_certificate ce join employee emp on emp.id=ce.employee_id join district dis on dis.id = emp.district_id "
				+ "WHERE dis.id = d.id GROUP BY emp.district_id HAVING count(*) = 1),0) equal1,"
				+ "COALESCE((SELECT count(*)  from tbl_certificate ce join employee emp on emp.id=ce.employee_id join district dis on dis.id = emp.district_id "
				+ "WHERE dis.id = d.id GROUP BY emp.district_id HAVING count(*) = 2),0) equal2,COALESCE((SELECT count(*)  from tbl_certificate ce join employee emp on emp.id=ce.employee_id join district dis on dis.id = emp.district_id "
				+ "WHERE dis.id = d.id GROUP BY emp.district_id HAVING count(*) > 2),0) moreThan2 from district as d "
				+ "LEFT JOIN province as pro on d.province_id = pro.id WHERE pro.name = :text";
		
		Query q = entityManager.createNativeQuery(sql);
		q.setParameter("text", dto.getKeyword());
		
	      
	    @SuppressWarnings("unchecked")
		List<Object[]> resultSql = (List<Object[]>) q.getResultList();
		 	      
		List<HightChart5DisDto> hightChart5DisDtos = new ArrayList<>();
		   
		for(Object[] objects : resultSql ) {
			HightChart5DisDto var = new HightChart5DisDto(objects);
			   hightChart5DisDtos.add(var);
		   }
	    
	      
	     
		return hightChart5DisDtos;
	}
	
	

}
