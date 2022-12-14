package com.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class SessionUser {
	
	String userName;
	String completeName;
	String email;
	String imageUrl;
	String idSesion;
	String phone;
	String role;
	private Users users;
	
		
	
	public SessionUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SessionUser(String userName, String completeName, String email) {
		super();
		this.userName = userName;
		this.completeName = completeName;
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getCompleteName() {
		return completeName;
	}
	
	public void setCompleteName(String completeName) {
		this.completeName = completeName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(String idSesion) {
		this.idSesion = idSesion;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}


	
	
	

}
