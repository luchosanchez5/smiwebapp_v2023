package com.expandable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expandable.model.CustomerShip;
import com.expandable.model.ExpCustomer;

public interface CustomerShipRep extends JpaRepository<CustomerShip, String> {
	
	List<CustomerShip> findAll();
	
}
