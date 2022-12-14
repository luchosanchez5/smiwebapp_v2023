package com.expandable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.expandable.model.ExpCustomer;

@Transactional
public interface ExpCustomerRep extends JpaRepository<ExpCustomer, String> {
     
	List<ExpCustomer> findAll();
	
}
