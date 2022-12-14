package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Incoterms;

public interface IncotermsRep extends JpaRepository<Incoterms, Long> {
	
	List<Incoterms> findAllByStatus(String stat);

}
