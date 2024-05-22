package com.chaitanya.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaitanya.jpa.DepartmentJPA;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentJPA, Long> {

	List<DepartmentJPA> findAllByCompanyId(Long long1);

}