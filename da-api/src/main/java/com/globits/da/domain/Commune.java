package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table(name = "commune")
public class Commune extends BaseObject {
    private static final long serialVersionUID = 1L;

    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "district_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private District district;
    
    @OneToMany(mappedBy = "commune")
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

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}


}
