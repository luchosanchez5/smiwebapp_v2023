package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.ItemFile;

@Transactional
public interface ItemFileRep extends JpaRepository<ItemFile, Integer> {
	
	List<ItemFile> findAllByStatReg(String stat);
	
}
