package com.service;

import java.util.List;

import com.expandable.model.PackingSlip;
import com.expandable.model.Sofst;
import com.model.PackDimension;
import com.model.Shipment;
import com.request.ShipmentsRequest;
import com.response.PackingslipResponse;
import com.response.ShipmentDetailsResponse;
import com.response.ShipmentResponse;
import com.response.ShipmentsResponse;

public interface ShippingService {
	
	public List<PackingSlip> listPendingShipments();
	public PackingslipResponse getPackingById(String packingNo);
	public ShipmentDetailsResponse saveShipment(ShipmentResponse shipRe, String packingslipNost, String user, String custshiAcc);
	public ShipmentsResponse findAllShipments();
	public ShipmentsResponse findAllTodayShipments();
	public ShipmentsResponse findAllShipmentsBySo(String soId);
	public ShipmentsResponse findAllShipmentsByCarrier(long carrierId);
	public ShipmentsResponse findAllShipmentsByCustomer(String customerId);
	public Boolean updatepackingTracking(String soId, Double batchNumber, String tracking);
	public boolean sendAsnNotifications(Shipment ship, String soId, Double batchNumber, PackingslipResponse packRes);
	public Shipment findOneById(long id);
	public List<PackDimension> findAllPackDimensions();
	public void cancelShipment(long id);
	
	
	                                    

		
		
}
