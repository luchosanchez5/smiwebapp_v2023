package com.response;

public class ShipmentResponse {
	
	private String result;
	private LabelResponse labelResponse;
	private ShipmentDetailsResponse shipDetails;
	private BadRequestResponse badRequest;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public LabelResponse getLabelResponse() {
		return labelResponse;
	}

	public void setLabelResponse(LabelResponse labelResponse) {
		this.labelResponse = labelResponse;
	}

	public ShipmentDetailsResponse getShipDetails() {
		return shipDetails;
	}

	public void setShipDetails(ShipmentDetailsResponse shipDetails) {
		this.shipDetails = shipDetails;
	}

	public BadRequestResponse getBadRequest() {
		return badRequest;
	}

	public void setBadRequest(BadRequestResponse badRequest) {
		this.badRequest = badRequest;
	}
	
	
	
	
	
	

}
