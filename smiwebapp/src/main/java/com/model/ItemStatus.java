package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="item_status")
public class ItemStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "item_status_desc")
	private String itemStatusDesc;
	@Column(name = "action")
	private String action;
	@Column(name = "stat_reg")
	private String statReg;
	@Column(name = "ordened")
	private String ordened;     // Y o N
	@Column (name = "estimate")
	private String estimate; // Y o N
	
	@Column (name = "pending")
	private String pending; // Y o N
	
	
	public long getId() {
		return id;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getItemStatusDesc() {
		return itemStatusDesc;
	}
	
	public void setItemStatusDesc(String itemStatusDesc) {
		this.itemStatusDesc = itemStatusDesc;
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






	public String getOrdened() {
		return ordened;
	}


	public void setOrdened(String ordened) {
		this.ordened = ordened;
	}


	public String getEstimate() {
		return estimate;
	}


	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}


	public String getPending() {
		return pending;
	}


	public void setPending(String pending) {
		this.pending = pending;
	}
	
	
	
	
	
	
	
}
