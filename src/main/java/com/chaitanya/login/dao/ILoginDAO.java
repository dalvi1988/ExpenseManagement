package com.chaitanya.login.dao;

import com.chaitanya.employee.model.EmployeeDTO;
import com.chaitanya.jpa.EmployeeJPA;
import com.chaitanya.jpa.LoginJPA;

public interface ILoginDAO {

	LoginJPA findByUserName(String username);

	int updatePassword(EmployeeDTO employeeDTO, String password);

	LoginJPA getLoginDetailByEmployeeId(EmployeeJPA employeeJPA);

}