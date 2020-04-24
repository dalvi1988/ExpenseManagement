package com.chaitanya.department.dao;

import java.util.List;

import com.chaitanya.jpa.DepartmentJPA;
import com.chaitanya.jpa.EmployeeJPA;


public interface IDepartmentDAO {

	public DepartmentJPA add(DepartmentJPA department);
	
	public List<DepartmentJPA> findAll();

}
