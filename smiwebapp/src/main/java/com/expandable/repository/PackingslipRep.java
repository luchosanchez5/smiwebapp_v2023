package com.expandable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expandable.model.PackingSlip;

public interface PackingslipRep extends JpaRepository<PackingSlip, String> {
	
	List<PackingSlip> findAll();
	PackingSlip findOneByPackingSlipno(String packingno);
	

}
