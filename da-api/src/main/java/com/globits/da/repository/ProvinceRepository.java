package com.globits.da.repository;

import com.globits.da.domain.Province;
import com.globits.da.dto.ProvinceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ProvinceRepository extends JpaRepository<Province, UUID> {
    @Query("select new com.globits.da.dto.ProvinceDto(ad) from Province  ad")
    Page<ProvinceDto> getListAddress(Pageable pageable);
}
