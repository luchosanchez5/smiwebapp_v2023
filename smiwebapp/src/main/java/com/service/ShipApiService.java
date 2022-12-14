package com.service;

import java.io.IOException;
import java.util.List;

import com.request.ShipmentRequest;
import com.request.ShipmentsRequest;
import com.response.CarriersResponse;
import com.response.ShipmentResponse;
import com.response.VoidLabelResponse;

public interface ShipApiService {
	
	public CarriersResponse listAPICarriersAccounts() throws IOException;
	public ShipmentResponse createShipment(String shipReq, String packingslipNo, String user, String custshipAcc) throws IOException;
	public VoidLabelResponse cancelLabel(String apiLabelId, String apiShippingId, long idShipment);
	

}
