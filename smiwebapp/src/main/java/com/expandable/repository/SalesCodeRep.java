package com.expandable.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expandable.model.SalesCode;

public interface SalesCodeRep extends JpaRepository<SalesCode, String> {
	
	

}
