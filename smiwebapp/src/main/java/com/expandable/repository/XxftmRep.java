package com.expandable.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.expandable.model.Xxftm;

public interface XxftmRep extends JpaRepository<Xxftm, String> {
	
	List<Xxftm> findAll();
	
	@Query(value="SELECT [TERMS_CODE],[TERMS_DESC],[DISCOUNT_DAYS],[DUE_DAYS]\r\n" + 
			     "FROM [ESIDBDeMO2].[dbo].[VexpTerms]",nativeQuery=true )
	List<Object[]> findAllTermsExp();
	
	
	
	

}
