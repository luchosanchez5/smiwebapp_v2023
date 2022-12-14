package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.PackDimension;

public interface PackdimensionRep extends JpaRepository<PackDimension, Long> {
	
	List<PackDimension> findAll();
	
	

}
