package com.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShipmentCostResponse {
	
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
