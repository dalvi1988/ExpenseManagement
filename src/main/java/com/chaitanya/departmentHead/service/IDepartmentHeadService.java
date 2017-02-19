package com.chaitanya.departmentHead.service;

import java.util.List;

import com.chaitanya.Base.BaseDTO;
import com.chaitanya.departmentHead.model.DepartmentHeadDTO;

public interface IDepartmentHeadService {

	List<DepartmentHeadDTO> findDepartmentHeadUnderCompany(BaseDTO baseDTO);

}
