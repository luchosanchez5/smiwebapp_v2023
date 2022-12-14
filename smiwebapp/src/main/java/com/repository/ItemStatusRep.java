package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.ItemStatus;

@Transactional
public interface ItemStatusRep extends JpaRepository<ItemStatus, Integer> {
	
	List<ItemStatus> findAllByStatReg(String stat);
	ItemStatus findOneById(long stat);
	List<ItemStatus> findAllByAction(String act);
	List<ItemStatus> findAllByPending(String cond);
	List<ItemStatus> findAllByOrdened(String cond);
	
	
	
}
