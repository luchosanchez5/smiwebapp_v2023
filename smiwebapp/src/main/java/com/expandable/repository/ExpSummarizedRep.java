package com.expandable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.expandable.model.ExpSummarized;

@Transactional
public interface ExpSummarizedRep extends JpaRepository<ExpSummarized, String> {
	
	List<ExpSummarized> findAllByPartId(String part);

}
