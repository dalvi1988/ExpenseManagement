package com.chaitanya.departmentHead.service;

import java.text.ParseException;
import java.util.List;

import com.chaitanya.base.BaseDTO;
import com.chaitanya.departmentHead.model.DepartmentHeadDTO;

public interface IDepartmentHeadService {

	List<DepartmentHeadDTO> findDepartmentHeadUnderBranch(BaseDTO baseDTO);

	BaseDTO addDepartmentHead(BaseDTO baseDTO) throws ParseException;

}
