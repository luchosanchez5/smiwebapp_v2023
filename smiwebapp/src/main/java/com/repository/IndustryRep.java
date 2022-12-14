package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Industry;

@Transactional
public interface IndustryRep extends JpaRepository<Industry, Integer> {

	List<Industry> findAllByStatReg(String stat);
	
	
}
