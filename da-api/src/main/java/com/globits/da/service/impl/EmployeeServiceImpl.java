package com.globits.da.service.impl;

import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Employee;
import com.globits.da.domain.Province;
import com.globits.da.dto.CategoryDto;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.EmployeeService;
import com.globits.core.service.impl.GenericServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    public EntityManager manager;
    
    @Autowired
    public ProvinceRepository provinceRepository;
    
    @Autowired
    public DistrictRepository districtRepository;
    
    @Autowired
    public CommuneRepository communeRepository;

    @Override
    public Page<EmployeeDTO> getPage(int pageindex,int pagesize) {
        Pageable pageable = PageRequest.of(pageindex-1,pagesize);
     return employeeRepository.getListPage(pageable);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public List<EmployeeSearchDTO> searchDTO(String name) {
        return employeeRepository.findEmplDTO(name);
    }

    @Override
    public EmployeeDTO saveOrUpdate(UUID id, EmployeeDTO dto) {
        if(dto != null){
            String erroMessage="";
            Employee entity = null;
            if(id != null){
                entity = employeeRepository.findById(id).orElse(null);

            }
            if (id == null){
                entity = new Employee();
            }
            //add province
            Province province = null;
            if(dto.getProvinceDto() != null && dto.getProvinceDto().getId() != null) {
            	province = provinceRepository.getOne(dto.getProvinceDto().getId());
            }
            
            //add District
            District district = null;
            if(dto.getDistrictDto() != null && dto.getDistrictDto().getId() != null) {
            	district = districtRepository.getOne(dto.getDistrictDto().getId());
            }
            
            //add Commune
            Commune commune = null;
            if(dto.getCommuneDto() != null && dto.getCommuneDto().getId() != null) {
            	commune = communeRepository.getOne(dto.getCommuneDto().getId());
            }
            
            
        //check code
            String code =dto.getCode();
            int checkcode=0;
            for(char c : code.toCharArray()){
                if(Character.isWhitespace(c)){
                    checkcode++;
                }
            }
            if(checkcode>0 || employeeRepository.findByCode(code) != 0){
            //    entity = null;
                EmployeeDTO employeeDTO = new EmployeeDTO();
                erroMessage="Code đã tồn tại!";
                employeeDTO.setErroMessage(erroMessage);
                return employeeDTO;
            }else {
                entity.setCode(code);
                entity.setName(dto.getName());
                entity.setEmail(dto.getEmail());
                entity.setPhone(dto.getPhone());
                entity.setAge(dto.getAge());
                entity.setProvince(province);
                entity.setDistrict(district);
                entity.setCommune(commune);
                entity = employeeRepository.save(entity);
                
                return new EmployeeDTO(entity,false);
            }


//
//            if(entity != null){
//                EmployeeDTO employeeDTO = new EmployeeDTO(entity);
//
//                return employeeDTO;
//
//            }

        }
        return null;
    }

    @Override
    public Boolean deleteByID(UUID id) {
        if(id != null ){
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<EmployeeDTO> searchByPage(EmployeeSearchDTO dto) {
        if(dto == null){
            return null;
        }
        int pageIndex = dto.getPageIndex();
        int pageSize = dto.getPageIndex();

        if( pageIndex > 0){
            pageIndex--;
        }else {
            pageIndex = 0;
        }

        String whereClause="";
       // String orderBy = "ORDER BY entity.cre"
        String orderBy = " ORDER BY entity.createDate DESC";
        String sqlCount = "select count(entity.id) from  Employee as entity where (1=1)";
        String sql = "select new com.globits.da.dto.EmployeeDTO(entity,false) from  Employee as entity where (1=1)  ";

        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
            whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text )";
        }
        sql += whereClause + orderBy;
        sqlCount += whereClause;


        Query q = manager.createQuery(sql, EmployeeDTO.class);
        Query qCount = manager.createQuery(sqlCount);

        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
            q.setParameter("text",'%'+dto.getKeyword()+'%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        @SuppressWarnings("unchecked")
        List<EmployeeDTO> entities = q.getResultList();
       
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<EmployeeDTO> result = new PageImpl<EmployeeDTO>(entities, pageable, count);


        return result;

    }

	@Override
	public Page<EmployeeDTO> report(int pageIndex,int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		return employeeRepository.getReport(pageable);
	}


}
