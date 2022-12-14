package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.Item;
import com.model.ItemActivity;

@Transactional
public interface ItemactivityRep extends JpaRepository<ItemActivity, Long> {
	
	List<ItemActivity> findAllByStatRecordAndItem(String stat, Item item);
	ItemActivity findOneByIdItemActivity(long id);

}
