package com.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsuranceCostResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("amount")
	private Double amount;
	
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	

}
