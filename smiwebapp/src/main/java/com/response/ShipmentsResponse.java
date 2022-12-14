package com.response;

import java.util.List;

import com.model.Shipment;

public class ShipmentsResponse {
	
	private String result;
	private String error;
	private List<Shipment> shipmentList;
	private int totalRecords;
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	public List<Shipment> getShipmentList() {
		return shipmentList;
	}
	
	public void setShipmentList(List<Shipment> shipmentList) {
		this.shipmentList = shipmentList;
	}
	
	public int getTotalRecords() {
		return totalRecords;
	}
	
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	

}
