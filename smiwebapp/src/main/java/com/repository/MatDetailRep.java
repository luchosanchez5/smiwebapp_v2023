package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Item;
import com.model.MatDetails;

@Transactional
public interface MatDetailRep extends JpaRepository<MatDetails, Integer> {
	
	List<MatDetails> findAllByItemAndStatRegOrderById(Item item, String statReg);
	MatDetails findOneById(long id);
	List<MatDetails> findAllByItemAndStatRegAndTypeDetailOrderById(Item item, String statReg, String type);


}
