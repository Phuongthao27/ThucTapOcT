package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.search.DistrictSearchDto;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.service.DistrictService;
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
public class DistrictServiceImpl extends GenericServiceImpl<District, UUID> implements DistrictService {
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProvinceRepository provinceRepository;
    @Autowired
    private CommuneRepository communeRepository;
    @Override
    public Page<DistrictDto> getPage(int pageindex, int pagesize) {
        Pageable pageable = PageRequest.of(pageindex,pagesize);
        return districtRepository.getListPage(pageable);
    }

    @Override
    public DistrictDto saveOrUpdate(UUID id, DistrictDto dto) {
        if(dto != null){
            District entity = null;
            if(id != null){
                entity = districtRepository.findById(id).orElse(null);

            }
            if(id == null){
                entity = new District();

            }
            Province province = null;

            if( dto.getProvinceDto() != null && dto.getProvinceDto().getId() != null){
                province = provinceRepository.getOne(dto.getProvinceDto().getId());
            }
            entity.setProvince(province);
            entity.setCode(dto.getCode());
            entity.setName(dto.getName());

            entity = districtRepository.save(entity);

            if(entity != null) {
                return new DistrictDto(entity,false);
            }
        }
        return null;
    }

    @Override
    public Boolean deleteByID(UUID id) {
        if(id != null){
            districtRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public Page<DistrictDto> searchByPage(DistrictSearchDto dto) {
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
        String sqlCount = "select count(entity.id) from District as entity where(1=1)";
        String sql = "select new com.globits.da.dto.DistrictDto(entity,false) from District as entity where(1=1)";

        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
            whereClause += "AND entity.name LIKE :text or entity.code LIKE :text";
        }
        sql += whereClause;
        sqlCount += whereClause;

        Query q = entityManager.createQuery(sql);
        Query qCount = entityManager.createQuery(sqlCount);
        if(dto.getKeyword() != null && StringUtils.hasText(dto.getKeyword())){
            q.setParameter("text",'%'+dto.getKeyword()+'%');
            qCount.setParameter("text",'%'+dto.getKeyword()+'%');
        }

        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);

        List<DistrictDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<DistrictDto> page = new PageImpl<DistrictDto>(entities,pageable,count);

        return page;
    }
}
