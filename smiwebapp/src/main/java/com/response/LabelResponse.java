package com.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LabelResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("label_id")
	private String labelId;
	@JsonProperty("status")
	private String status;
	@JsonProperty("shipment_id")
	private String shipmentId;
	@JsonProperty("ship_date")
	private LocalDateTime shipDate;
	@JsonProperty("create_at")
	private String createAt;
	@JsonProperty("shipment_cost")
	private ShipmentCostResponse shipmentCost;
	@JsonProperty("insurance_cost")
	private InsuranceCostResponse insuranceCost;
	@JsonProperty("charge_event")
	private String chageEvent;
	@JsonProperty("tracking_number")
	private String trackingNumber;
	@JsonProperty("is_return_label")
	private Boolean isReturnLabel;
	@JsonProperty("rma_number")
	private String rmaNumber;
	@JsonProperty("is_international")
	private Boolean isInternational;
	@JsonProperty("batch_id")
	private String batchId;
	@JsonProperty("carrier_id")
	private String carrierId;
	@JsonProperty("service_code")
	private String serviceCode;
	@JsonProperty("package_code")
	private String packageCode;
	@JsonProperty("voided")
	private Boolean voided;
	@JsonProperty("voided_at")
	private LocalDateTime voidedAt;
	@JsonProperty("label_format")
	private String labelFormat;
	@JsonProperty("label_layout")
	private String labelLayout;
	@JsonProperty("trackable")
	private Boolean trackable;
	@JsonProperty("label_image_id")
	private String labelImageId;
	@JsonProperty("carrier_code")
	private String carrierCode;
	@JsonProperty("tracking_status")
	private String trackingStatus;
	private List<PackageTypeResponse> packages;
	@JsonProperty("label_download")
	private LabelDownloadResponse labelDownload;

	
	
	
	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	public LocalDateTime getShipDate() {
		return shipDate;
	}

	public void setShipDate(LocalDateTime shipDate) {
		this.shipDate = shipDate;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	public ShipmentCostResponse getShipmentCost() {
		return shipmentCost;
	}

	public void setShipmentCost(ShipmentCostResponse shipmentCost) {
		this.shipmentCost = shipmentCost;
	}

	public InsuranceCostResponse getInsuranceCost() {
		return insuranceCost;
	}

	public void setInsuranceCost(InsuranceCostResponse insuranceCost) {
		this.insuranceCost = insuranceCost;
	}

	public String getChageEvent() {
		return chageEvent;
	}

	public void setChageEvent(String chageEvent) {
		this.chageEvent = chageEvent;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Boolean getIsReturnLabel() {
		return isReturnLabel;
	}

	public void setIsReturnLabel(Boolean isReturnLabel) {
		this.isReturnLabel = isReturnLabel;
	}

	public String getRmaNumber() {
		return rmaNumber;
	}

	public void setRmaNumber(String rmaNumber) {
		this.rmaNumber = rmaNumber;
	}

	public Boolean getIsInternational() {
		return isInternational;
	}

	public void setIsInternational(Boolean isInternational) {
		this.isInternational = isInternational;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

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

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public Boolean getVoided() {
		return voided;
	}

	public void setVoided(Boolean voided) {
		this.voided = voided;
	}

	public LocalDateTime getVoidedAt() {
		return voidedAt;
	}

	public void setVoidedAt(LocalDateTime voidedAt) {
		this.voidedAt = voidedAt;
	}

	public String getLabelFormat() {
		return labelFormat;
	}

	public void setLabelFormat(String labelFormat) {
		this.labelFormat = labelFormat;
	}

	public String getLabelLayout() {
		return labelLayout;
	}

	public void setLabelLayout(String labelLayout) {
		this.labelLayout = labelLayout;
	}

	public Boolean getTrackable() {
		return trackable;
	}

	public void setTrackable(Boolean trackable) {
		this.trackable = trackable;
	}

	public String getLabelImageId() {
		return labelImageId;
	}

	public void setLabelImageId(String labelImageId) {
		this.labelImageId = labelImageId;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getTrackingStatus() {
		return trackingStatus;
	}

	public void setTrackingStatus(String trackingStatus) {
		this.trackingStatus = trackingStatus;
	}

	public List<PackageTypeResponse> getPackages() {
		return packages;
	}

	public void setPackages(List<PackageTypeResponse> packages) {
		this.packages = packages;
	}

	public LabelDownloadResponse getLabelDownload() {
		return labelDownload;
	}

	public void setLabelDownload(LabelDownloadResponse labelDownload) {
		this.labelDownload = labelDownload;
	}
	
	
	

}
