package com.chaitanya.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaitanya.jpa.BranchJPA;

@Repository
public interface BranchRepository extends JpaRepository<BranchJPA, Long> {

	// Get All Branch By Company Id
	List<BranchJPA> findAllBranchByCompanyId(Integer integer);

}