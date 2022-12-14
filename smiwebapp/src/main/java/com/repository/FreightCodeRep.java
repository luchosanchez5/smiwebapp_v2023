package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.FreightCode;

public interface FreightCodeRep extends JpaRepository<FreightCode, Long> {
	
	FreightCode findOneByCode(String code);
	
	

}
