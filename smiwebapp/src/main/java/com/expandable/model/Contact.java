package com.expandable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="v_app_asn_contacts")
public class Contact {

	@Column(name = "CUSTOMER_ID", columnDefinition="nchar(8)", nullable=false)
	private String customerId;
	
	@Id
	@Column(name = "CUST_CONTACT", columnDefinition="nvarchar(50)", nullable=false)
	private String custContact;
	
	@Column(name = "LAST_NAME", columnDefinition="nvarchar(24)", nullable=false)
	private String lastName;
	
	@Column(name = "FIRST_NAME", columnDefinition="nvarchar(24)", nullable=false)
	private String firstName;
	
	@Column(name = "E_MAIL", columnDefinition="nvarchar(64)", nullable=false)
	private String email;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustContact() {
		return custContact;
	}

	public void setCustContact(String custContact) {
		this.custContact = custContact;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}
