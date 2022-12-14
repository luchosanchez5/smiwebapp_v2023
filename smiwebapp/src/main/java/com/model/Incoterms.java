package com.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Incoterms")
public class Incoterms {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "incoterm_desc")
	private String incotermDesc;
	
	@Column(name = "created_date")
	private LocalDateTime createdDate;

	@Column(name = "stat_auth")
	private String statAuth;
	
	@Column(name = "status")
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIncotermDesc() {
		return incotermDesc;
	}

	public void setIncotermDesc(String incotermDesc) {
		this.incotermDesc = incotermDesc;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getStatAuth() {
		return statAuth;
	}

	public void setStatAuth(String statAuth) {
		this.statAuth = statAuth;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
	

}
