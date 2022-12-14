package com.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarriersResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<CarrierResponse> carriers;	
	@JsonProperty("request_id")
	private String requestId;
	private List<ErrorResponse> errors;
	
	
	public List<CarrierResponse> getCarriers() {
		return carriers;
	}
	public void setCarriers(List<CarrierResponse> carriers) {
		this.carriers = carriers;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public List<ErrorResponse> getErrors() {
		return errors;
	}
	public void setErrors(List<ErrorResponse> errors) {
		this.errors = errors;
	}
	
	

}
