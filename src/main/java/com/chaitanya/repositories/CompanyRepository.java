package com.chaitanya.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chaitanya.jpa.CompanyJPA;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyJPA, Long> {

}