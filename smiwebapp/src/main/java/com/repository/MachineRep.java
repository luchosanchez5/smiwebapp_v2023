package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Machine;

@Transactional
public interface MachineRep extends JpaRepository<Machine, Integer> {
	
	List<Machine> findAllByStatReg(String stat);
	
	
}
