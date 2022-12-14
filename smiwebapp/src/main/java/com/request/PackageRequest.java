package com.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PackageRequest implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WeightRequest weight;
	private DimensionRequest dimensions;
	@JsonProperty("label_messages")
	private LabelMessagesRequest labelMessages;
	
	
	public WeightRequest getWeight() {
		return weight;
	}
	public void setWeight(WeightRequest weight) {
		this.weight = weight;
	}
	public DimensionRequest getDimensions() {
		return dimensions;
	}
	public void setDimensions(DimensionRequest dimensions) {
		this.dimensions = dimensions;
	}
	public LabelMessagesRequest getLabelMessages() {
		return labelMessages;
	}
	public void setLabelMessages(LabelMessagesRequest labelMessages) {
		this.labelMessages = labelMessages;
	}
	
	
	
	

}
