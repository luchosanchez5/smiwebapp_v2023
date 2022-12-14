package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="quote_sub_status")
public class QuoteSubStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "substatus_desc")
	private String substatusDesc;
	@Column(name = "quote_status")
	private String quoteStatus;
	@Column(name = "stat_won")
	private String statWon;
	@Column(name = "stat_lost")
	private String statLost;
	@Column(name = "stat_reg")
	private String statReg;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getSubstatusDesc() {
		return substatusDesc;
	}
	
	public void setSubstatusDesc(String substatusDesc) {
		this.substatusDesc = substatusDesc;
	}
	public String getQuoteStatus() {
		return quoteStatus;
	}
	public void setQuoteStatus(String quoteStatus) {
		this.quoteStatus = quoteStatus;
	}
	public String getStatWon() {
		return statWon;
	}
	public void setStatWon(String statWon) {
		this.statWon = statWon;
	}
	public String getStatLost() {
		return statLost;
	}
	public void setStatLost(String statLost) {
		this.statLost = statLost;
	}
	public String getStatReg() {
		return statReg;
	}
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}
	
	 
	
	

}
