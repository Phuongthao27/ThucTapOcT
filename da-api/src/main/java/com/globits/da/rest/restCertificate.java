package com.globits.da.rest;

import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.CertificateDtoSearch;
import com.globits.da.dto.search.DistrictSearchDto;
import com.globits.da.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/certificate")
public class restCertificate {
    @Autowired
    private CertificateService certificateService;

    @GetMapping("page/{pageIndex}/{pageSize}")
    public ResponseEntity<Page> getListPage(@PathVariable int pageIndex,@PathVariable int pageSize ){
        return new ResponseEntity<>(certificateService.getPage(pageIndex,pageSize),HttpStatus.OK);
    }

    @PostMapping()
    public CertificateDto saveOrUpdate(  @RequestBody CertificateDto dto){
       return certificateService.saveOrUpdate(null,dto);
    }
    @PostMapping("/{id}")
    public CertificateDto saveOrUpdate(@PathVariable UUID id, @RequestBody CertificateDto dto){
        return certificateService.saveOrUpdate(id,dto);
    }

    @PostMapping("id/{id}")
    public ResponseEntity<Boolean> DeleteById(@PathVariable UUID id){
        return new ResponseEntity<>(certificateService.deleteByid(id), HttpStatus.OK);
    }
    @PostMapping("result")
    public Page<CertificateDto> searchByPage(@RequestBody CertificateDtoSearch certificateDtoSearch) {
        return certificateService.searchByPage(certificateDtoSearch);
    }

}
