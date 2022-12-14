package com.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.model.UserRole;


@Transactional
public interface UserRoleRep extends CrudRepository<UserRole, Integer> {

}
