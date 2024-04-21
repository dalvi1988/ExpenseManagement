package com.chaitanya.login.dao;

import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.LoginJPA;
import com.chaitanya.login.model.LoginDTO;

public interface ILoginDAO {

	LoginJPA findByUserName(String username);
	
	LoginJPA findByUserName(LoginDTO loginDTO);

	int updatePassword(EmployeeDTO employeeDTO, String password);

	LoginJPA getLoginDetailByEmployeeId(EmployeeDTO employeeDTO);

}