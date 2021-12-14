package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.CommuneSearchDto;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.service.CommuneService;
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
public class CommuneServiceImpl extends GenericServiceImpl<Commune, UUID> implements CommuneService {
    @Autowired
    private CommuneRepository communeRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private EntityManager entityManager;
    @Override
    public Page<CommuneDto> getPage(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return communeRepository.getListPage(pageable);
    }

    @Override
    public CommuneDto saveOrUpdate(UUID id, CommuneDto communeDto) {
        if( communeDto != null){
            Commune entity = null;
            if(id != null){
                entity = communeRepository.findById(id).orElse(null);

            }

            if(id == null){
                entity = new Commune();

            }
            District district = null;
            if( communeDto.getDistrictDto() != null && communeDto.getDistrictDto().getId() != null){
                district = districtRepository.getOne(communeDto.getDistrictDto().getId());
            }
            entity.setCode(communeDto.getCode());
            entity.setName(communeDto.getName());
            entity.setDistrict(district);

            entity =  communeRepository.save(entity);
            if(entity != null){
                return  new CommuneDto(entity,false);
            }


        }
        return null;
    }

    @Override
    public Boolean deleteById(UUID id) {
        if(id != null){
            communeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<CommuneDto> searchByPage(CommuneSearchDto dto) {
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
        String sqlCount = "select count(entity.id) from Commune as entity where(1=1)";
        String sql = "select new com.globits.da.dto.CommuneDto(entity,fasle) from Commune as entity where(1=1)";

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

        List<CommuneDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex,pageSize);
        Page<CommuneDto> page = new PageImpl<CommuneDto>(entities,pageable,count);

        return page;
    }
}
