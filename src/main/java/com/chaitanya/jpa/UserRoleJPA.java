package com.chaitanya.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRoleJPA {

	@Id
	@Column(name = "user_role_id", unique = true, nullable = false)
	private Long userRoleId;

	@Column(name = "role", nullable = false)
	private String role;

	@Column(name = "login_id")
	private Long loginId;

	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public UserRoleJPA() {
	}

	public Long getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}