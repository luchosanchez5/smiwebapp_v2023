package com.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdvancedOptionsRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("bill_to_account")
	private String billtoAccount;
	@JsonProperty("bill_to_party")
	private String billtoParty;
	@JsonProperty("custom_field1")
	private String custom_field1;
	@JsonProperty("custom_field2")
	private String custom_field2;
	@JsonProperty("custom_field3")
	private String custom_field3;
	
	
	public String getBilltoAccount() {
		return billtoAccount;
	}
	
	public void setBilltoAccount(String billtoAccount) {
		this.billtoAccount = billtoAccount;
	}
	
	public String getBilltoParty() {
		return billtoParty;
	}
	
	public void setBilltoParty(String billtoParty) {
		this.billtoParty = billtoParty;
	}
	
	public String getCustom_field1() {
		return custom_field1;
	}
	
	public void setCustom_field1(String custom_field1) {
		this.custom_field1 = custom_field1;
	}
	
	public String getCustom_field2() {
		return custom_field2;
	}
	
	public void setCustom_field2(String custom_field2) {
		this.custom_field2 = custom_field2;
	}
	
	public String getCustom_field3() {
		return custom_field3;
	}
	
	public void setCustom_field3(String custom_field3) {
		this.custom_field3 = custom_field3;
	}
	
	
}
