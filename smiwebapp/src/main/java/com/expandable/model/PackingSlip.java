package com.expandable.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="v_app_packingslip")
public class PackingSlip {
	
	@Id
	@Column(name = "PackingSlip_no", columnDefinition="nvarchar(18)", nullable=false)
	private String packingSlipno;
	
	@Column(name = "so_id", columnDefinition="nchar(8)", nullable=false)
	private String soId;
	
	@Column(name = "cust_po_id", columnDefinition="nvarchar(60)", nullable=false)
	private String custPo;
	
	@Column(name = "reference", columnDefinition="nvarchar(16)", nullable=false)
	private String reference;
	
	@Column(name = "date_approved", columnDefinition="datetime", nullable=false)
	private LocalDateTime dateApproved;
	
	@Column(name = "ship_to_cust", columnDefinition="nchar(8)", nullable=false)
	private String shipTo;
	
	@Column(name = "so_line_status", columnDefinition="nchar(1)", nullable=false)
	private String solineStatus;
	
	@Column(name = "batch_number", columnDefinition="decimal(6,0)", nullable=false)
	private String batchNumber;
		
	@Column(name = "customer_name", columnDefinition="nvarchar(255)", nullable=false)
	private String customerName;
	
	@Column(name = "address_1", columnDefinition="nvarchar(255)", nullable=false)
	private String address1;
	
	@Column(name = "address_2", columnDefinition="nvarchar(255)", nullable=false)
	private String address2;
	
	@Column(name = "address_3", columnDefinition="nvarchar(255)", nullable=false)
	private String address3;
	
	@Column(name = "city", columnDefinition="nvarchar(50)", nullable=false)
	private String city;
	
	@Column(name = "state", columnDefinition="nchar(3)", nullable=false)
	private String state;
	
	@Column(name = "zip_code", columnDefinition="nchar(10)", nullable=false)
	private String zipCode;		
	
	@Column(name = "country", columnDefinition="nchar(15)", nullable=false)
	private String country;
		
	@Column(name = "so_phone_no", columnDefinition="nvarchar(30)", nullable=false)
	private String sophoneNumber;
	
	@Column(name = "carrier", columnDefinition="nvarchar(3)", nullable=false)
	private String carrier;
	
	@Column(name = "contact_name", columnDefinition="nvarchar(200)", nullable=false)
	private String contactName;
	
	@Column(name = "phone_no", columnDefinition="nvarchar(25)", nullable=false)
	private String phoneNo;
	
	@Column(name = "e_mail", columnDefinition="nvarchar(40)", nullable=false)
	private String email;
		
	@Column(name = "customer_id", columnDefinition="nchar(8)", nullable=false)
	private String customerId;
	
	@Column(name = "taker_name", columnDefinition="nvarchar(32)", nullable=false)
	private String takerName;
	
	@Column(name = "email_taker", columnDefinition="nvarchar(64)", nullable=false)
	private String emailTaker;	
	
	@Column(name = "bill_of_lading", columnDefinition="nvarchar(48)", nullable=false)
	private String billofLading;
	
	@Column(name = "freight_code", columnDefinition="nchar(1)", nullable=false)
	private String freightCode;
	
	@Column(name = "bill_to_account", columnDefinition="nvarchar(24)", nullable=false)
	private String billtoAccount;
	


	public String getPackingSlipno() {
		return packingSlipno;
	}

	public void setPackingSlipno(String packingSlipno) {
		this.packingSlipno = packingSlipno;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getCustPo() {
		return custPo;
	}

	public void setCustPo(String custPo) {
		this.custPo = custPo;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public LocalDateTime getDateApproved() {
		return dateApproved;
	}

	public void setDateApproved(LocalDateTime dateApproved) {
		this.dateApproved = dateApproved;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public String getSolineStatus() {
		return solineStatus;
	}

	public void setSolineStatus(String solineStatus) {
		this.solineStatus = solineStatus;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
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

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
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

	public String getTakerName() {
		return takerName;
	}

	public void setTakerName(String takerName) {
		this.takerName = takerName;
	}

	public String getEmailTaker() {
		return emailTaker;
	}

	public void setEmailTaker(String emailTaker) {
		this.emailTaker = emailTaker;
	}

	public String getBillofLading() {
		return billofLading;
	}

	public void setBillofLading(String billofLading) {
		this.billofLading = billofLading;
	}

	public String getFreightCode() {
		return freightCode;
	}

	public void setFreightCode(String freightCode) {
		this.freightCode = freightCode;
	}

	public String getBilltoAccount() {
		return billtoAccount;
	}

	public void setBilltoAccount(String billtoAccount) {
		this.billtoAccount = billtoAccount;
	}	
	
	
	
	

}
