package com.globits.da.domain;

import com.globits.core.auditing.AuditableEntity;
import com.globits.core.domain.BaseObject;
import com.globits.core.dto.BaseObjectDto;
import lombok.Data;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name="Employee")

public class Employee extends BaseObject {
private static final long serialVersionUID = 1L;


    @Column(name = "code")
    private String code ;
    @Column(name = "name")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "age")
    private Integer age;

    @OneToMany(mappedBy = "employee")
    private Set<Certificate> certificates = new HashSet<>();
    
    @ManyToOne()
    @JoinColumn(name = "province_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Province province;
    
    @ManyToOne()
    @JoinColumn(name = "district_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private District district;
    
    @ManyToOne()
    @JoinColumn(name = "commune_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Commune commune;
    

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Set<Certificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(Set<Certificate> certificates) {
		this.certificates = certificates;
	}
	
	public Province getProvince() {
		return province;
	}
	public void setProvince(Province province) {
		this.province = province;
	}
	public District getDistrict() {
		return district;
	}
	public void setDistrict(District district) {
		this.district = district;
	}
	public Commune getCommune() {
		return commune;
	}
	public void setCommune(Commune commune) {
		this.commune = commune;
	}




}
