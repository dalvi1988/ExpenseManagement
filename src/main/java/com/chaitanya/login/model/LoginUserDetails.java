package com.chaitanya.login.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class LoginUserDetails extends User{
	private static final long serialVersionUID = 1L;
	
	private LoginDTO loginDTO;
	public LoginUserDetails(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities,LoginDTO loginDTO) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities);
		this.setLoginDTO(loginDTO);
		// TODO Auto-generated constructor stub
	}
	public LoginDTO getLoginDTO() {
		return loginDTO;
	}
	public void setLoginDTO(LoginDTO loginDTO) {
		this.loginDTO = loginDTO;
	}
}
