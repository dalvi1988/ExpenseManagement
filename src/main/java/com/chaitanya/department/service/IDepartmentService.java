package com.chaitanya.department.service;

import java.util.List;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.department.model.DepartmentDTO;

public interface IDepartmentService {

	BaseDTO addDepartment(BaseDTO baseDTO);

	List<DepartmentDTO> findAll();

}
