package com.globits.da.rest;

import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.EmployeeSearchDTO;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/province")
public class RestProvince {
    @Autowired
    private ProvinceService provinceService;

    @PostMapping("")
    public ResponseEntity<ProvinceDto> saveOrUpdate(@RequestBody ProvinceDto dto){
        return new ResponseEntity<>(provinceService.saveOrUpdate(null,dto), HttpStatus.CREATED);
    }
    @PostMapping("id/{id}")
    public ResponseEntity<ProvinceDto> saveOrUpdate(@PathVariable UUID id, @RequestBody ProvinceDto dto){
        return new ResponseEntity<>(provinceService.saveOrUpdate(id,dto), HttpStatus.CREATED);
    }
    @PostMapping("/{id}")
    public ResponseEntity<Boolean> DeleteById(@PathVariable UUID id){
        return new ResponseEntity<>(provinceService.deleteById(id),HttpStatus.OK);
    }

    @GetMapping("page/{pageIndex}/{pageSize}")
    public ResponseEntity<Page> getListPage(@PathVariable int pageIndex,@PathVariable int pageSize){
        return new ResponseEntity<>(provinceService.getPage(pageIndex,pageSize),HttpStatus.OK);
    }
    @PostMapping("result")
    public Page<ProvinceDto> searchByPage(@RequestBody ProvinceSearchDto provinceSearchDto) {
        return provinceService.searchByPage(provinceSearchDto);
    }


}
