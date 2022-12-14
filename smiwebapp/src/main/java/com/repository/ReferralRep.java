package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Referral;

@Transactional
public interface ReferralRep extends JpaRepository<Referral, Integer> {
	
	List<Referral> findAllByStatReg(String stat);
	
}
