package com.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsnRequest implements Serializable {
	
	@JsonProperty("id_shipment")	
	private long idShipment;
	
	@JsonProperty("packingslip_no")
	private String packingslipNo;

	public long getIdShipment() {
		return idShipment;
	}

	public void setIdShipment(long idShipment) {
		this.idShipment = idShipment;
	}

	public String getPackingslipNo() {
		return packingslipNo;
	}

	public void setPackingslipNo(String packingslipNo) {
		this.packingslipNo = packingslipNo;
	}
	
	
	
	

}
