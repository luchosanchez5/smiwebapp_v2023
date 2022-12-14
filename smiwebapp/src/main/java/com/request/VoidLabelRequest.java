package com.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VoidLabelRequest {
	
	@JsonProperty("api_label_id")
	private String apiLabelId;
	@JsonProperty("api_shipping_id")
	private String apiShippingId;
	@JsonProperty("shipping_id")
	private long shippingId;
	
	
	public String getApiLabelId() {
		return apiLabelId;
	}
	public void setApiLabelId(String apiLabelId) {
		this.apiLabelId = apiLabelId;
	}
	public String getApiShippingId() {
		return apiShippingId;
	}
	public void setApiShippingId(String apiShippingId) {
		this.apiShippingId = apiShippingId;
	}
	public long getShippingId() {
		return shippingId;
	}
	public void setShippingId(long shippingId) {
		this.shippingId = shippingId;
	}
	
	
	

}
