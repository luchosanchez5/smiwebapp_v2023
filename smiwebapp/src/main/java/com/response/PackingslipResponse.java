package com.response;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PackingslipResponse {
	
	@JsonProperty("packingslip_no")
	private String PackingSlipno;
	
	@JsonProperty("so_id")
	private String soId;
	
	@JsonProperty("part_id")
	private String partId;
	
	@JsonProperty("cust_po")
	private String custPo;
	
	@JsonProperty("ship_to_cust")
	private String shipTo;
		
	@JsonProperty("customer_name")
	private String customerName;
	
	@JsonProperty("address_1")
	private String address1;
	
	@JsonProperty("address_2")
	private String address2;
	
	@JsonProperty("address_3")
	private String address3;

	private String city;

	private String state;
	
	@JsonProperty("zip_code")
	private String zipCode;		

	private String country;
		
	@JsonProperty("so_phone_no")
	private String sophoneNumber;
	
	@JsonProperty("carrier_id")
	private String carrierId;
	
	@JsonProperty("contact_name")
	private String contactName;
	
	@JsonProperty("phone_no")
	private String phoneNo;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("customer_id")
	private String customerId;
	
	@JsonProperty("batch_number")
	private Double batchNumber;
	    
	@JsonProperty("freight")
	private String freight;
		
	@JsonProperty("bill_to_account")
	private String billtoAccount;
	


	public String getPackingSlipno() {
		return PackingSlipno;
	}

	public void setPackingSlipno(String packingSlipno) {
		PackingSlipno = packingSlipno;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getCustPo() {
		return custPo;
	}

	public void setCustPo(String custPo) {
		this.custPo = custPo;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSophoneNumber() {
		return sophoneNumber;
	}

	public void setSophoneNumber(String sophoneNumber) {
		this.sophoneNumber = sophoneNumber;
	}

	public String getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Double getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(Double batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getFreight() {
		return freight;
	}

	public void setFreight(String freight) {
		this.freight = freight;
	}

	public String getBilltoAccount() {
		return billtoAccount;
	}

	public void setBilltoAccount(String billtoAccount) {
		this.billtoAccount = billtoAccount;
	}
	
	
	
	
	
}
