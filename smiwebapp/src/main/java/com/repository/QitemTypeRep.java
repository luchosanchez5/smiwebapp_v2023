package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.QitemType;

@Transactional
public interface QitemTypeRep extends JpaRepository<QitemType, Integer> {
	
	List<QitemType> findAllByStatReg(String stat);
	
}
