package com.globits.da.repository;

import com.globits.da.domain.Commune;
import com.globits.da.dto.CommuneDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CommuneRepository extends JpaRepository<Commune, UUID> {
    @Query ("select new com.globits.da.dto.CommuneDto(entity,false) from Commune  as entity")
    Page<CommuneDto> getListPage(Pageable pageable);
}

