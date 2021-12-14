package com.globits.da.rest;

import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.DistrictSearchDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.service.DistrictService;
import com.globits.da.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("api/district")
public class restDistrict {
    @Autowired
    private DistrictService districtService;

    @PostMapping("")
    public ResponseEntity<DistrictDto> saveOrUpdate(@RequestBody DistrictDto dto){
        return new ResponseEntity<>(districtService.saveOrUpdate(null,dto), HttpStatus.CREATED);
    }
    @PostMapping("id/{id}")
    public ResponseEntity<DistrictDto> saveOrUpdate(@PathVariable UUID id,@RequestBody DistrictDto dto){
        return new ResponseEntity<>(districtService.saveOrUpdate(id,dto), HttpStatus.CREATED);
    }


    @PostMapping("/{id}")
    public ResponseEntity<Boolean> DeleteById(@PathVariable UUID id){
        return new ResponseEntity<>(districtService.deleteByID(id),HttpStatus.OK);
    }

    @GetMapping("page/{pageIndex}/{pageSize}")
    public ResponseEntity<Page> getListPage(@PathVariable int pageIndex, @PathVariable int pageSize){
        return new ResponseEntity<>(districtService.getPage(pageIndex,pageSize),HttpStatus.OK);
    }
    @PostMapping("result")
    public Page<DistrictDto> searchByPage(@RequestBody DistrictSearchDto districtSearchDto) {
        return districtService.searchByPage(districtSearchDto);
    }
}
