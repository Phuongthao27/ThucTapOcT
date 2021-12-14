package com.globits.da.service.impl;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.da.domain.Certificate;
import com.globits.da.dto.CertificateDto;
import com.globits.da.repository.CertificateRepository;
import com.globits.da.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CertificateServiceImpl extends GenericServiceImpl<Certificate, UUID> implements CertificateService {

    @Autowired
    private CertificateRepository certificateRepo;

//    @Override
//    public List<CertificateDto> getAll() {
////        List<Certificate> lst = certificateRepo.findAll();
////        List<CertificateDto> result = new ArrayList<>();
////        for (Certificate cer: lst) {
////            result.add(new CertificateDto(cer));
////        }
//        List<CertificateDto> result = certificateRepo.getAll();
//        return result;
//    }

    @Override
    public Certificate delete(UUID id) {
        return null;
    }

    @Override
    public Certificate save(Certificate certificate) {
        return null;
    }

    @Override
    public Page<Certificate> getList(int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public Certificate findById(UUID id) {
        return null;
    }
}
