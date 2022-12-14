package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.model.MeasureUnit;

@Transactional
public interface MeasureUnitRep extends JpaRepository<MeasureUnit, Integer> {
	
	List<MeasureUnit> findAllByStatReg(String stat);
	MeasureUnit findOneByIdMeasureunit(long id);
	

}
