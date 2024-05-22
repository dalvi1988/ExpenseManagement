package com.chaitanya.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaitanya.jpa.EmployeeJPA;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeJPA, Long> {

	List<EmployeeJPA> findAllByCompanyId(Long long1);

	List<EmployeeJPA> findAllByDepartmentIdBranchId(Long departmentId, Long branchId);

	EmployeeJPA findByEmployeeId(Long employeeId);

	EmployeeJPA findByEmail(String emailId);

}