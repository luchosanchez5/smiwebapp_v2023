package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="qitem_type")
public class QitemType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "descitem_type")
	private String descitemType;
	@Column(name = "action")
	private String action;
	@Column(name = "stat_reg")
	private String statReg;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDescitemType() {
		return descitemType;
	}
	
	public void setDescitemType(String descitemType) {
		this.descitemType = descitemType;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getStatReg() {
		return statReg;
	}
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}
	
	
	
	
	
	
}
