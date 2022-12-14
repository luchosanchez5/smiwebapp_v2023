package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.ItemType;

@Transactional
public interface ItemTypeRep extends JpaRepository<ItemType, Integer> {
	
	List<ItemType> findAllByStatReg(String stat);

}
