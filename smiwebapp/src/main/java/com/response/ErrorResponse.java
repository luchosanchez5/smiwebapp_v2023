package com.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("error_source")
	private String errorSource;
	@JsonProperty("error_type")
	private String errorType;
	@JsonProperty("error_code")
	private String errorCode;
	@JsonProperty("message")
	private String message;
	@JsonProperty("carrier_id")
	private String carrierId;
	@JsonProperty("carrier_code")
	private String carrierCode;
	@JsonProperty("carrier_name")
	private String carrierName;
	
	public String getErrorSource() {
		return errorSource;
	}
	
	public void setErrorSource(String errorSource) {
		this.errorSource = errorSource;
	}
	
	public String getErrorType() {
		return errorType;
	}
	
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
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
	
	public String getCarrierName() {
		return carrierName;
	}
	
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	
	
	
	

}
