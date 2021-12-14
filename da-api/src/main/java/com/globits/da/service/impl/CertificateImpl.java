package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.*;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.search.CertificateDtoSearch;
import com.globits.da.repository.CertificateRepository;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

@Service
public class CertificateImpl  extends GenericServiceImpl<Certificate, UUID> implements CertificateService {
    @Autowired
    public EntityManager manager;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public CertificateDto saveOrUpdate(UUID id,CertificateDto dto) {
        if(dto != null ) {
            Certificate entity = null;
            if(id !=null) {
                entity =  certificateRepository.findById(id).orElse(null);
            }
            if(entity == null) {
                entity = new Certificate();
            }
            entity.setDate(dto.getDate());
            entity.setName(dto.getName());

            Employee employee = null;
            Province province = null;
            if(dto.getEmployee() != null && dto.getEmployee().getId() != null){
                employee = employeeRepository.getOne(dto.getEmployee().getId());

            }
            if(dto.getProvince() != null && dto.getProvince().getId() != null){
                province = provinceRepository.getOne(dto.getProvince().getId());
            }
            entity.setEmployee(employee);
            entity.setProvince(province);
            entity = certificateRepository.save(entity);
            if (entity != null) {
                return new CertificateDto(entity);
            }
        }
        return null;
        }


    @Override
    public Boolean deleteByid(UUID id) {
        if ( id != null){
            certificateRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<CertificateDto> getPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
              return  certificateRepository.getListPage(pageable);
    }

    @Override
    public Page<CertificateDto> searchByPage(CertificateDtoSearch dto) {
        if(dto == null){
            return null;
        }
        int pageIndex = dto.getPageIndex();
        int pageSize = dto.getPageSize();

        if(pageIndex > 0){
            pageIndex--;
        }else {
            pageIndex = 0;
        }
        String whereClause= "";
        String sqlCount = "select count(entity.id) from Certificate as entity where(1=1)";
        String sql = "select new com.globits.da.dto.CertificateDto(entity) from Certificate as entity where(1=1)";

        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
            whereClause += "AND entity.name LIKE :text ";
        }
        sql += whereClause;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql);
        Query qCount = manager.createQuery(sqlCount);
        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
            q.setParameter("text",'%'+dto.getKeyword()+'%');
            qCount.setParameter("text",'%'+dto.getKeyword()+'%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);

        List<CertificateDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<CertificateDto> page = new PageImpl<CertificateDto>(entities,pageable,count);

        return page;
    }
}
