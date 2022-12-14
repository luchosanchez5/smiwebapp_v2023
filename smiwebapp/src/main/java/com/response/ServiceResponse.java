package com.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceResponse implements Serializable {
	
	
	@JsonProperty("carrier_id")
	private String carrierId;
	@JsonProperty("carrier_code")
	private String carrierCode;
	@JsonProperty("service_code")
	private String serviceCode;
	private String name;
	private Boolean domestic;
	private Boolean international;
	private Boolean isMultiPackageSupported;
	
	
	public String getCarrierId() {
		return carrierId;
	}
	
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getDomestic() {
		return domestic;
	}
	public void setDomestic(Boolean domestic) {
		this.domestic = domestic;
	}
	public Boolean getInternational() {
		return international;
	}
	public void setInternational(Boolean international) {
		this.international = international;
	}
	public Boolean getIsMultiPackageSupported() {
		return isMultiPackageSupported;
	}
	public void setIsMultiPackageSupported(Boolean isMultiPackageSupported) {
		this.isMultiPackageSupported = isMultiPackageSupported;
	}
	
	
	

}
