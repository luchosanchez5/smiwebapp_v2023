package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="machine")
public class Machine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "desc_machine")
	private String descMachine;
	@Column(name = "stat_reg")
	private String statReg;
	
	
	public long getId() {
		return id;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getDescMachine() {
		return descMachine;
	}
	
	
	public void setDescMachine(String descMachine) {
		this.descMachine = descMachine;
	}
	
	
	public String getStatReg() {
		return statReg;
	}
	
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}
	
	
}
