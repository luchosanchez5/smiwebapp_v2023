package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Customer;

@Transactional
public interface CustomerRep extends JpaRepository<Customer, Integer> {
  
	List<Customer> findAllByStatReg(String stat);
	List<Customer> findAllByStatCompanyAndStatReg(String statC, String statR);
	Customer findOneById(long id);
	Customer findOneByIdCompany(String id);
	boolean existsByIdCompany(String id);
	
}
