package com.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BadRequestResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("request_id")
	private String requestId;
	@JsonProperty("errors")
	private List<ErrorResponse> errors;
	
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
