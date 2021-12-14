package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Category;
import com.globits.da.domain.Certificate;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.CertificateDtoSearch;
import com.globits.da.dto.search.ProvinceSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface CertificateService  extends GenericService<Certificate, UUID> {
    CertificateDto saveOrUpdate(UUID id,CertificateDto dto);
    Boolean deleteByid(UUID id);
    Page<CertificateDto> getPage(int pageIndex, int pageSize);
    Page<CertificateDto> searchByPage(CertificateDtoSearch certificateDtoSearch);
}
