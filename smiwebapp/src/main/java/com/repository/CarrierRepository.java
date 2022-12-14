package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Carrier;
import com.model.Estimator;

public interface CarrierRepository extends JpaRepository<Carrier, Long> {
	
	List<Carrier> findAllByStatus(String status);
	Carrier findOneByExpCode(String exCode);
	Carrier findOneBycarrierId(String carrierId);
}
