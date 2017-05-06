package com.chaitanya.login.service;

import java.text.ParseException;

import com.chaitanya.base.BaseDTO;

public interface ILoginService {
	BaseDTO forgotPassword(BaseDTO baseDTO) throws ParseException;
}
