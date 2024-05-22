package com.chaitanya.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaitanya.jpa.DepartmentHeadJPA;

@Repository
public interface DepartmentHeadRepository extends JpaRepository<DepartmentHeadJPA, Long> {

	List<DepartmentHeadJPA> findAllByBranchId(Long branchId);

	DepartmentHeadJPA findByBranchIdDepartmentId(Long departmentId, Long branchId);

}