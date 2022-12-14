package com.expandable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_APP_PARTS")
public class ExpPart {
	
	@Id
	@Column(name = "PART_ID", columnDefinition="nchar(25)", nullable=false)
	private String partId;
	
	@Column(name = "PART_TYPE", columnDefinition="nchar(1)", nullable=false)
	private String partType;
	
	@Column(name = "PART_DESC", columnDefinition="nchar(255)", nullable=false)
	private String partDesc;
	
	@Column(name = "PART_UM", columnDefinition="nchar(2)", nullable=false)
	private String partUM;
	
	@Column(name = "STD_UNIT_COST", columnDefinition="nchar(25)", nullable=false)
	private double unitCost;
	
	@Column(name = "CUSTOMER_ID", columnDefinition="nchar(8)", nullable=false)
	private String customerId;
	
	@Column(name = "CUST_PART_ID", columnDefinition="nvarchar(255)", nullable=false)
	private String custPartId;
	
	

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public String getPartDesc() {
		return partDesc;
	}

	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}

	public String getPartUM() {
		return partUM;
	}

	public void setPartUM(String partUM) {
		this.partUM = partUM;
	}

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustPartId() {
		return custPartId;
	}

	public void setCustPartId(String custPartId) {
		this.custPartId = custPartId;
	}
	
	
	
	
	
	
	
	
	
	

}
