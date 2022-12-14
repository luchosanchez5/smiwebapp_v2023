package com.expandable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.expandable.model.Contact;

public interface ContactRep extends JpaRepository<Contact, String> {

	List<Contact> findAllByCustomerId(String customerId);
	
		
}
