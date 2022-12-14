package com.model;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="referral")
public class Referral {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "referralfull_name")
	private String referralfullName;
	@Column(name = "stat_reg")
	private String statReg;
	
	
	public long getId() {
		return id;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getReferralfullName() {
		return referralfullName;
	}
	
	public void setReferralfullName(String referralfullName) {
		this.referralfullName = referralfullName;
	}
	
	public String getStatReg() {
		return statReg;
	}
	
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}
	
	

}
