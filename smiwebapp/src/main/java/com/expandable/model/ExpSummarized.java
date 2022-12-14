package com.expandable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_PART_SUMMARIZED")
public class ExpSummarized {
	
	
	@Column(name = "PART_ID", columnDefinition="nchar(25)", nullable=false)
	private String partId;
	
	@Id
	@Column(name = "STORES_CODE", columnDefinition="nchar(2)", nullable=false)
	private String storeCode;
	
	@Column(name = "ON_HAND_QTY", columnDefinition="decimal(38,4)", nullable=false)
	private double onhandQty;
	
	@Column(name = "SUPPLY_QTY", columnDefinition="decimal(38,4)", nullable=false)
	private double supplyQty;
	
	@Column(name = "DEMAND_QTY", columnDefinition="decimal(38,4)", nullable=false)
	private double demandQty;
	
	@Column(name = "SO_ALLOCATIONS", columnDefinition="decimal(38,4)", nullable=false)
	private double soAllocations;
	
	@Column(name = "NET_QTY", columnDefinition="decimal(38,4)", nullable=false)
	private double netQty;	
	
	@Column(name = "NET_ALLOCATIONS", columnDefinition="decimal(38,4)", nullable=false)
	private double netAllocations;
	
	

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public double getOnhandQty() {
		return onhandQty;
	}

	public void setOnhandQty(double onhandQty) {
		this.onhandQty = onhandQty;
	}

	public double getSupplyQty() {
		return supplyQty;
	}

	public void setSupplyQty(double supplyQty) {
		this.supplyQty = supplyQty;
	}

	public double getDemandQty() {
		return demandQty;
	}

	public void setDemandQty(double demandQty) {
		this.demandQty = demandQty;
	}

	public double getSoAllocations() {
		return soAllocations;
	}

	public void setSoAllocations(double soAllocations) {
		this.soAllocations = soAllocations;
	}

	public double getNetQty() {
		return netQty;
	}

	public void setNetQty(double netQty) {
		this.netQty = netQty;
	}

	public double getNetAllocations() {
		return netAllocations;
	}

	public void setNetAllocations(double netAllocations) {
		this.netAllocations = netAllocations;
	}	
	
	
	
	
	
	
	
	
	
	

}
