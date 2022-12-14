package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="freight_code")
public class FreightCode {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "code")
	private String code;
	@Column(name = "description")
	private String description;
	@Column(name = "customerCharges")
	private String customerCharges;
	@Column(name = "thirdPartyPayment")
	private String thirdPartyPayment;
	@Column(name = "percentCharges")
	private Double percentCharges;
	@Column(name = "status")
	private String status;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCustomerCharges() {
		return customerCharges;
	}
	
	public void setCustomerCharges(String customerCharges) {
		this.customerCharges = customerCharges;
	}
	
	public String getThirdPartyPayment() {
		return thirdPartyPayment;
	}
	
	public void setThirdPartyPayment(String thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}
	
	public Double getPercentCharges() {
		return percentCharges;
	}
	
	public void setPercentCharges(Double percentCharges) {
		this.percentCharges = percentCharges;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
		
	
	
	

}
