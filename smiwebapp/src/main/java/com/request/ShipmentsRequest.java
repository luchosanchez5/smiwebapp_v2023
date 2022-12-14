package com.request;

import java.io.Serializable;

public class ShipmentsRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ShipmentRequest shipment;
	
	
	public ShipmentRequest getShipment() {
		return shipment;
	}

	public void setShipment(ShipmentRequest shipment) {
		this.shipment = shipment;
	}

	

}
