package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name ="province")
@XmlRootElement
public class Province extends BaseObject {
    private static final long serialVersionUID = 1L;

    @Column( name ="code")
    private String code;

    @Column(name = "name")
    private String name;


    @OneToMany(mappedBy = "province")
    private Set<District> districts = new HashSet<>();
    
    @OneToMany(mappedBy = "province")
    private Set<Employee> employees = new HashSet<>();
    
   

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Set<District> getDistricts() {
		return districts;
	}


	public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}





}
