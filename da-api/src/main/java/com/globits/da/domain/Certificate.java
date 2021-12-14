package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.xmlbeans.GDate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
@Getter
@Setter
@Entity
@Table(name = "tbl_certificate")
@XmlRootElement
public class Certificate extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "name")
	private String name;
	@Column(name="date")
	private Date date;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "province_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Province province;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}


}
