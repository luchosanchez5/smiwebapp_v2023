package com.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShipmentRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("carrier_id")
	private String carrierId;
	@JsonProperty("service_code")
	private String serviceCode;
	@JsonProperty("ship_to")
	private AddressRequest shipTo;
	@JsonProperty("ship_from")
	private AddressRequest shipFrom;
	private List<PackageRequest> packages;
	@JsonProperty("advanced_options")
	private AdvancedOptionsRequest advancedOptions;
	

	public String getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}

	public String getServiceCode() {
		return serviceCode;
	}
	
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	public AddressRequest getShipTo() {
		return shipTo;
	}
	
	public void setShipTo(AddressRequest shipTo) {
		this.shipTo = shipTo;
	}
	
	public AddressRequest getShipFrom() {
		return shipFrom;
	}
	
	public void setShipFrom(AddressRequest shipFrom) {
		this.shipFrom = shipFrom;
	}
	
	public List<PackageRequest> getPackages() {
		return packages;
	}
	
	public void setPackages(List<PackageRequest> packages) {
		this.packages = packages;
	}

	public AdvancedOptionsRequest getAdvancedOptions() {
		return advancedOptions;
	}

	public void setAdvancedOptions(AdvancedOptionsRequest advancedOptions) {
		this.advancedOptions = advancedOptions;
	}

	
	
	
}
