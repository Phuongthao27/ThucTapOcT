package com.globits.da.repository;

import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDTO;
import com.globits.da.dto.search.EmployeeSearchDTO;
import org.hibernate.sql.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    @Query("select new com.globits.da.dto.search.EmployeeSearchDTO(ep) from Employee ep where lower(ep.name)  like lower(concat('%',:name,'%') )")
    List<EmployeeSearchDTO> findEmplDTO(String name);
    @Query("select count(ep.id)from Employee as ep where ep.code =?1 ")
    Integer findByCode(String code);

    @Query("select new com.globits.da.dto.EmployeeDTO(ep,false) from Employee ep")
    Page<EmployeeDTO> getListPage(Pageable pageable);
    
    @Query("select new com.globits.da.dto.EmployeeDTO(entity,false) from Employee as entity left join Certificate as c on "
    		+ "entity.id = c.employee.id group by c.employee.id  having count(c.employee.id)>2")
    Page<EmployeeDTO> getReport(Pageable pageable);

}
