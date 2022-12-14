package com.expandable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_APP_CUSTOMERS")
public class ExpCustomer {
	
	@Id
	@Column(name = "CUSTOMER_ID", columnDefinition="nchar(8)", nullable=false)
	private String customerId;
	
	@Column(name = "CUSTOMER_NAME", columnDefinition="nchar(255)", nullable=false)
	private String customerName;
	
	@Column(name = "ADDRESS_1", columnDefinition="nvarchar(255)", nullable=false)
	private String address1;
	
	@Column(name = "ADDRESS_2", columnDefinition="nvarchar(255)", nullable=false)
	private String address2;
	
	@Column(name = "ADDRESS_3", columnDefinition="nvarchar(255)", nullable=false)
	private String address3;
	
	@Column(name = "CITY", columnDefinition="nvarchar(50)", nullable=false)
	private String city;
	
	@Column(name = "STATE", columnDefinition="nchar(3)", nullable=false)
	private String state;
	
	@Column(name = "ZIP_CODE", columnDefinition="nchar(10)", nullable=false)
	private String zipCode;
	
	@Column(name = "SO_CONTACT", columnDefinition="nvarchar(50)", nullable=false)
	private String soContact;
	
	@Column(name = "SO_PHONE_NO", columnDefinition="nvarchar(30)", nullable=false)
	private String sophoneNo;
	
	@Column(name = "FAX_NO", columnDefinition="nvarchar(30)", nullable=false)
	private String faxNo;
	
	@Column(name = "TERMS_CODE", columnDefinition="nchar(2)", nullable=false)
	private String termsCode;
	
	@Column(name = "SALESMAN_ID", columnDefinition="nchar(3)", nullable=false)
	private String salesmanId;
	
	@Column(name = "TERMS_DESC", columnDefinition="nvarchar(24)", nullable=false)
	private String termsDesc;
	
	@Column(name = "DESC_SALES", columnDefinition="nvarchar(32)", nullable=false)
	private String descSales;
	

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public String getSoContact() {
		return soContact;
	}

	public void setSoContact(String soContact) {
		this.soContact = soContact;
	}

	public String getSophoneNo() {
		return sophoneNo;
	}

	public void setSophoneNo(String sophoneNo) {
		this.sophoneNo = sophoneNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getTermsCode() {
		return termsCode;
	}

	public void setTermsCode(String termsCode) {
		this.termsCode = termsCode;
	}

	public String getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}

	public String getTermsDesc() {
		return termsDesc;
	}

	public void setTermsDesc(String termsDesc) {
		this.termsDesc = termsDesc;
	}

	public String getDescSales() {
		return descSales;
	}

	public void setDescSales(String descSales) {
		this.descSales = descSales;
	}
	

}
