package com.service;

import com.model.Users;

public interface UserService {
	
	public Users findUserByEmail(String email);
	public Users findUserByusername(String user);
	public void saveUser(Users user);
	public void changePassword(Users user);
	
}
