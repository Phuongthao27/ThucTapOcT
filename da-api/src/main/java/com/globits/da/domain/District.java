package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.Data;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity

@Table(name= "district")
public class District extends BaseObject {
    private static final long serialVersionUID = 1L;

    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "province_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Province province;

    @OneToMany(mappedBy = "district")
    private Set<Commune> communes = new HashSet<>();
    
    @OneToMany(mappedBy = "district")
    private Set<Employee> employees = new HashSet<>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Set<Commune> getCommunes() {
        return communes;
    }

    public void setCommunes(Set<Commune> communes) {
        this.communes = communes;
    }
}
