package com.chaitanya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaitanya.jpa.LoginJPA;

@Repository
public interface LoginRepository extends JpaRepository<LoginJPA, Long> {

	LoginJPA findByUserName(String username);

	int updatePassword(Long employeeId, String encode);

	LoginJPA findByEmployeeId(Long employeeId);

}