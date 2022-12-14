package com.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Material;

@Transactional
public interface MaterialRep extends JpaRepository<Material, Long> {
	
	List<Material> findAllByStatReg(String stat);
	List<Material> findAllByTypeM(String type);
	Material findOneByPartNumber(String partNumber);
	boolean existsByPartNumber(String parNumber);

}
