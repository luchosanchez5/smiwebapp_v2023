package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Shipment;

public interface ShipmentRep extends JpaRepository<Shipment, Long> {
	
	List<Shipment> findAllByStatus(String stat);
	Shipment findOneById(long id);

}
