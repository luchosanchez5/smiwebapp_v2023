package com.expandable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expandable.model.Sofst;

public interface SofstRep extends JpaRepository<Sofst, Double> {
	
	List<Sofst> findAllBySoIdAndBatchNumber(String soId, Double batchNumber);
	
}
