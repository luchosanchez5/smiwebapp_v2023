package com.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.model.Role;


@Transactional
public interface RoleRep extends CrudRepository<Role, Integer> {

	
	Role findById(int id);
	Role findByDescription(String role);
}
