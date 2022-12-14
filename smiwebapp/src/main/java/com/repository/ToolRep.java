package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Tool;

@Transactional
public interface ToolRep extends JpaRepository<Tool, Integer> {
	
	List<Tool> findAllByStatReg(String stat);

}
