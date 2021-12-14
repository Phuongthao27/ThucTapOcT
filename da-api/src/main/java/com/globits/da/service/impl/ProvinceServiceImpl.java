package com.globits.da.service.impl;

import com.globits.da.domain.Province;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.service.ProvinceService;
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
public class ProvinceServiceImpl implements ProvinceService {
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProvinceRepository addressRepository;
    @Override
    public Page<ProvinceDto> getPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        return addressRepository.getListAddress(pageable);
    }

    @Override
    public ProvinceDto saveOrUpdate(UUID id, ProvinceDto provinceDto) {
        if(provinceDto != null){
            Province entity = null;
            if(id != null){
                entity = addressRepository.findById(id).orElse(null);

            }
            if(id == null){
                entity = new Province();
            }

            entity.setCode(provinceDto.getCode());
            entity.setName(provinceDto.getName());


            addressRepository.save(entity);

            if(entity != null){
                return new ProvinceDto(entity);
            }
        }
        return null;
    }

    @Override
    public Boolean deleteById(UUID id) {
        if(id != null){
             addressRepository.deleteById(id);
             return true;
        }
        return false;
    }

    @Override
    public Page<ProvinceDto> searchByPage(ProvinceSearchDto dto) {
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
        String orderBy = " ORDER BY entity.createDate DESC";
        String whereClause= "";
        String sqlCount = "select count(entity.id) from Province as entity where(1=1)";
        String sql = "select new com.globits.da.dto.ProvinceDto(entity) from Province as entity where(1=1)";

        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
            whereClause += "AND entity.name LIKE :text or entity.code LIKE :text";
        }
        sql += whereClause+orderBy;
        sqlCount += whereClause;

        Query q = entityManager.createQuery(sql, ProvinceDto.class);
        Query qCount = entityManager.createQuery(sqlCount);

        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
            q.setParameter("text",'%'+dto.getKeyword()+'%');
            qCount.setParameter("text", '%' + dto.getKeyword() + '%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<ProvinceDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<ProvinceDto> result = new PageImpl<ProvinceDto>(entities, pageable, count);

        return result;
    }

}
