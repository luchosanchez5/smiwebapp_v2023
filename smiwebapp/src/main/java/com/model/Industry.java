package com.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="industry")
public class Industry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "industry_desc")
	private String industryDesc;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "stat_reg")
	private String statReg;
	
	
	public long getId() {
		return id;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getIndustryDesc() {
		return industryDesc;
	}
	
	public void setIndustryDesc(String industryDesc) {
		this.industryDesc = industryDesc;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getStatReg() {
		return statReg;
	}
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}
	
}
