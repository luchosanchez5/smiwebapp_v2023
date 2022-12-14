package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.TeslaModel;

@Transactional
public interface TeslaModelRep extends JpaRepository<TeslaModel, Long> {
	
	List<TeslaModel> findAllByStatReg(String stat);
	

}
