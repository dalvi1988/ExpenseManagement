package com.chaitanya.jpa;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="login_details")
public class LoginJPA {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="login_id")
	private Long loginId;
	
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private EmployeeJPA employeeJPA;
	
	private String password;
	
	@Column(name="user_name")
	private String userName;
	
	@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "login_id", referencedColumnName = "login_id")
	private Set<UserRoleJPA> userRole = new HashSet<UserRoleJPA>();
	
	public Long getLoginId() {
		return loginId;
	}
	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		
		this.password = password;
	}
	
	public Set<UserRoleJPA> getUserRole() {
		return this.userRole;
	}

	public void setUserRole(Set<UserRoleJPA> userRole) {
		this.userRole = userRole;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public EmployeeJPA getEmployeeJPA() {
		return employeeJPA;
	}
	public void setEmployeeJPA(EmployeeJPA employeeJPA) {
		this.employeeJPA = employeeJPA;
	}

}
