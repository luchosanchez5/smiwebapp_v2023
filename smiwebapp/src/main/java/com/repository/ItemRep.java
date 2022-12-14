package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.model.Estimator;
import com.model.Item;
import com.model.ItemStatus;
import com.model.Quote;
import com.model.Seller;

@Transactional
public interface ItemRep extends JpaRepository<Item, Long> {
	
	List<Item> findAllByStatRegAndQuoteOrderByIdItem(String stat, Quote q);
	List<Item> findAllByStatRegAndQuoteOrderByNewPartNameAscQuantityAsc(String stat, Quote q);
	List<Item> findAllByStatRegAndItemStatusOrderByQuote(String stat, ItemStatus itStatus);
	Item findOneByIdItem(long id);
	List<Item> findAllByStatReg(String stat);
	List<Item> findByStatRegAndNewPartNameContainsIgnoreCase(String stat, String keyword);
	int countByQuoteAndStatReg(Quote q, String statReg);
	int countByQuoteAndStatRegAndItemStatus(Quote q, String statReg, ItemStatus it);
	
	
	List<Item> findAllByStatRegAndItemStatusInOrderByQuote(String stat, List<ItemStatus> lSta);

	
	

	
	

}
