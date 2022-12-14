package com.response;

import com.model.Shipment;

public class ShipmentDetailsResponse {
	
	private Boolean updatedTracking;
	private Boolean result;
	private Shipment ship;
	
	
	public Boolean getUpdatedTracking() {
		return updatedTracking;
	}
	
	public void setUpdatedTracking(Boolean updatedTracking) {
		this.updatedTracking = updatedTracking;
	}
	
	public Boolean getResult() {
		return result;
	}
	
	public void setResult(Boolean result) {
		this.result = result;
	}
	
	public Shipment getShip() {
		return ship;
	}
	
	public void setShip(Shipment ship) {
		this.ship = ship;
	}
	


}
