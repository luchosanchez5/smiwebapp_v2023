package com.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AsnRespond {
	
	private String result;
	private String error;
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getError() {
		return error;
	}
	
	public void setError(String error) {
		this.error = error;
	}
	
	
}
