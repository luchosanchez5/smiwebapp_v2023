package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Part;

@Transactional
public interface PartRep extends JpaRepository<Part, Integer> {
		
	
	List<Part> findAllByStatReg(String stat);
	Part findByPartNumber(String part);
	boolean existsByPartNumber(String part);
	Part findOneById(long id);
	Part findOneByPartNumber(String part);
}
