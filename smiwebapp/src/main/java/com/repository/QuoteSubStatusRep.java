package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.QuoteSubStatus;

public interface QuoteSubStatusRep extends JpaRepository<QuoteSubStatus, Long> {

	QuoteSubStatus findOneById(long id);
	List<QuoteSubStatus> findAllByStatRegAndQuoteStatus(String statReg, String statQuote);
	
}
