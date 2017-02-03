package com.chaitanya.login.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chaitanya.jpa.LoginJPA;
import com.chaitanya.jpa.UserRoleJPA;
import com.chaitanya.login.convertor.LoginConvertor;
import com.chaitanya.login.dao.ILoginDAO;
import com.chaitanya.login.model.LoginDTO;
import com.chaitanya.login.model.LoginUserDetails;

@Service("loginDetailsService")
public class LoginDetailsService implements UserDetailsService {

	@Autowired
	private ILoginDAO loginDAO;

	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		LoginJPA loginDetails = loginDAO.findByUserName(username);
		List<GrantedAuthority> authorities=null;
		
		authorities = buildUserAuthority(loginDetails.getUserRole());
		LoginDTO loginDTO=LoginConvertor.setLoginToLoginDTO(loginDetails);
		return buildUserForAuthentication(loginDetails, authorities,loginDTO);
		
	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(LoginJPA loginDetails, List<GrantedAuthority> authorities,LoginDTO loginDTO) {
		return new LoginUserDetails(loginDetails.getUserName(), loginDetails.getPassword(), true, true, true, true, authorities,loginDTO);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRoleJPA> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserRoleJPA userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}