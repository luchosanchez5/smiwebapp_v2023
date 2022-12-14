package com.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShipmentBodyRequest implements Serializable {
	
	@JsonProperty("ship_re")
	private ShipmentsRequest shipRe;
	@JsonProperty("ship_re_jason")
	private String shipreJson;
	private String packingslipNo;
	private String user;
	@JsonProperty("cust_ship_account")
	private String custshipAccount;
	
	
	public ShipmentsRequest getShipRe() {
		return shipRe;
	}
	public void setShipRe(ShipmentsRequest shipRe) {
		this.shipRe = shipRe;
	}
	public String getShipreJson() {
		return shipreJson;
	}
	public void setShipreJson(String shipreJson) {
		this.shipreJson = shipreJson;
	}
	public String getPackingslipNo() {
		return packingslipNo;
	}
	public void setPackingslipNo(String packingslipNo) {
		this.packingslipNo = packingslipNo;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCustshipAccount() {
		return custshipAccount;
	}
	public void setCustshipAccount(String custshipAccount) {
		this.custshipAccount = custshipAccount;
	}
	
	
	
	
}
