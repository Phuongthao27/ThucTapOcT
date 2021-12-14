package com.globits.da.rest;

import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.dto.search.CommuneSearchDto;
import com.globits.da.dto.search.ProvinceSearchDto;
import com.globits.da.service.CommuneService;
import com.globits.da.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/api/commune")
public class RestCommuneController {
    @Autowired
    private CommuneService communeService;

    @PostMapping("")
    public ResponseEntity<CommuneDto> saveOrUpdate(@RequestBody CommuneDto dto){
        return new ResponseEntity<>(communeService.saveOrUpdate(null,dto), HttpStatus.CREATED);
    }
    @PostMapping("id/{id}")
    public ResponseEntity<CommuneDto> saveOrUpdate(@PathVariable UUID id, @RequestBody CommuneDto dto){
        return new ResponseEntity<>(communeService.saveOrUpdate(id,dto), HttpStatus.CREATED);
    }


    @PostMapping("/{id}")
    public ResponseEntity<Boolean> DeleteById(@PathVariable UUID id){
        return new ResponseEntity<>(communeService.deleteById(id),HttpStatus.OK);
    }

    @GetMapping("page/{pageIndex}/{pageSize}")
    public ResponseEntity<Page> getListPage(@PathVariable int pageIndex, @PathVariable int pageSize){
        return new ResponseEntity<>(communeService.getPage(pageIndex,pageSize),HttpStatus.OK);
    }
    @PostMapping("result")
    public Page<CommuneDto> searchByPage(@RequestBody CommuneSearchDto communeSearchDto) {
        return communeService.searchByPage(communeSearchDto);
    }
}
