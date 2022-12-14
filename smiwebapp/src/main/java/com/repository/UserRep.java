package com.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.model.Users;


@Transactional
public interface UserRep extends CrudRepository<Users, Integer> {
	
	Users findUserById(long id);
	Users findByUsername(String user);

}
