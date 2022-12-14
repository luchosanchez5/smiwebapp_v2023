package com.expandable.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.expandable.model.PackingSlip;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.PackDimension;
import com.model.QueryExp;
import com.model.Shipment;
import com.request.AsnRequest;
import com.request.ShipmentBodyRequest;
import com.request.ShipmentRequest;
import com.request.ShipmentsRequest;
import com.request.VoidLabelRequest;
import com.response.AsnRespond;
import com.response.CarrierResponse;
import com.response.CarriersResponse;
import com.response.PackingslipResponse;
import com.response.ShipmentResponse;
import com.response.ShipmentsResponse;
import com.response.VoidLabelResponse;
import com.service.ShipApiService;
import com.service.ShippingService;

@Controller
@RequestMapping(path = "/shipping")
public class ShippingController {
	
	@Autowired
	public ShippingService shipService;
	
	@Autowired
	public ShipApiService shiApi;
	
	@RequestMapping(value = "/packingslips/List", method = RequestMethod.GET)
	@ResponseBody
	private List<PackingSlip> findPendingPackingSlips(){
		System.out.println(shipService.listPendingShipments().toString());				
		return shipService.listPendingShipments();
				
	}
	
	@RequestMapping(value = "/packdimensions/List", method = RequestMethod.GET)
	@ResponseBody	
	private List<PackDimension> findPackDimensions(){
			return shipService.findAllPackDimensions();
				
	}
	
	
	@RequestMapping(value = "/carriers/list", method = RequestMethod.GET)
	@ResponseBody
	private CarriersResponse getCarriersList(){
		CarriersResponse carrierList = new CarriersResponse();
		try {
			carrierList = shiApi.listAPICarriersAccounts();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return carrierList;
		
	}
	
	@RequestMapping(value = "/packingslips/id", method = RequestMethod.GET)
	@ResponseBody
	private PackingslipResponse getPackingById(String id) {
		PackingslipResponse packing = new PackingslipResponse();
		try {
			 packing = shipService.getPackingById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return packing;
		
	}
	
	
	@RequestMapping(value = "/packingslips/create-shipment", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	private ShipmentResponse create_shipment(@RequestBody ShipmentBodyRequest shipRequest ) {
		ShipmentResponse ship = new ShipmentResponse();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			 System.out.println("JSON STRING MAPPER : " +  objectMapper.writeValueAsString(shipRequest.getShipRe()));
			 System.out.println("GETTING SHIP : " +  shipRequest.getShipRe().getShipment().getServiceCode());
			 System.out.println("JSOn SHIP REQUEST : " +  shipRequest.getShipreJson());
			 ship = shiApi.createShipment(objectMapper.writeValueAsString(shipRequest.getShipRe()), shipRequest.getPackingslipNo(), shipRequest.getUser(), shipRequest.getCustshipAccount());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ship;
		
	}
	
	
	@RequestMapping(value = "/labels/byCustomer", method = RequestMethod.GET)
	@ResponseBody
	private ShipmentsResponse getLabelsByCustomer(String customerId) {
		ShipmentsResponse labels = new ShipmentsResponse();
		try {
			System.out.println("CUSTOMER BY ID : " +  customerId);
			 labels =  shipService.findAllShipmentsByCustomer(customerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return labels;
		
	}
	
	@RequestMapping(value = "/labels/byToday", method = RequestMethod.GET)
	@ResponseBody
	private ShipmentsResponse getLabelsByToday() {
		ShipmentsResponse labels = new ShipmentsResponse();
		try {
			 labels =  shipService.findAllTodayShipments();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return labels;
		
	}
	
	
	@RequestMapping(value = "/send-asn", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	private AsnRespond sendAsn(@RequestBody AsnRequest asn ) {
		AsnRespond asnRes = new AsnRespond();
		String result = "N";
		try {
			
			Shipment ship = shipService.findOneById(asn.getIdShipment());
			PackingslipResponse pack = shipService.getPackingById(asn.getPackingslipNo());
			Boolean sendAsn =  shipService.sendAsnNotifications(ship, pack.getSoId(), pack.getBatchNumber(),pack);
		    if (sendAsn)
		    	asnRes.setResult("Y");
			
		} catch (Exception e) {
			e.printStackTrace();
			asnRes.setResult("N");
			asnRes.setError(e.getMessage());
		}
		return asnRes;
		
	}
	
	
	@RequestMapping(value = "/labels/voidLabel", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	private VoidLabelResponse voidLabel(@RequestBody VoidLabelRequest voidL) {
		VoidLabelResponse voidLabel = new VoidLabelResponse();
		ShipmentsResponse labels = new ShipmentsResponse();
		try {
			voidLabel = shiApi.cancelLabel(voidL.getApiLabelId(), voidL.getApiShippingId(), voidL.getShippingId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return voidLabel;
		
	}
	

}
