package com.chaitanya.login.service;

import java.text.ParseException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.login.model.LoginDTO;

public interface ILoginService {
	BaseDTO forgotPassword(BaseDTO baseDTO) throws ParseException;

	boolean validateOldPassword(BaseDTO baseDTO);

	BaseDTO updatePassword(BaseDTO baseDTO);

	LoginDTO authenticateUserForAPI(BaseDTO baseDTO) throws UsernameNotFoundException;
}
