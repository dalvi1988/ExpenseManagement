package com.chaitanya.departmentHead.dao;

import java.util.List;

import com.chaitanya.branch.model.BranchDTO;
import com.chaitanya.jpa.DepartmentHeadJPA;


public interface IDepartmentHeadDAO {

	public DepartmentHeadJPA add(DepartmentHeadJPA department);

	public List<DepartmentHeadJPA> findDepartmentHeadUnderBranch(
			BranchDTO branchDTO);
	
}
