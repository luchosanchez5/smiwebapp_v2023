package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Terms;


@Transactional
public interface TermsRep extends JpaRepository<Terms, Integer> {
	
	List<Terms> findAllByStatus(String stat);

}
