package com.expandable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.expandable.model.ExpPart;

@Transactional
public interface ExpPartRep extends JpaRepository<ExpPart, String> {
	
	ExpPart findOneByPartId(String id);
	List<ExpPart> findAllByPartType(String type);
	List<ExpPart> findAll();
	List<ExpPart> findAllByCustomerId(String custId);

}
