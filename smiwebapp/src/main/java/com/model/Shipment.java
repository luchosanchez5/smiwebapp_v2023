package com.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.expandable.model.PackingSlip;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.response.InsuranceCostResponse;
import com.response.ShipmentCostResponse;

@Entity
@Table(name="Shipment")
public class Shipment {
	
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SHIP_SEQ", sequenceName = "SHIP_SEQ", initialValue = 1, allocationSize=1)
    @GeneratedValue(strategy = SEQUENCE, generator = "SHIP_SEQ")
    private long id;
    @Column(name = "api_label_id")
    private String apiLabelId;
    @Column(name = "api_shipping_id")
    private String apiShippingId;
    @Column(name = "shipment_cost")
    private Double shipmentCost;
	@Column(name = "insurance_cost")
	private Double insuranceCost;
	@Column(name = "tracking_number")
	private String trackingNumber;
	@Column(name  = "is_international")
	private Boolean isInternational;
	@ManyToOne
	@JoinColumn(name="id_carrier",referencedColumnName = "id")	
	private Carrier carrier;
	@Column(name  = "service_desc")
	private String serviceDesc;
	@Column(name = "package_code")
	private String packageCode;
	@Column(name = "voided")
	private Boolean voided;
	@Column(name = "voided_at")
	private LocalDateTime voidedAt;
	@Column(name = "label_pdf")
	private String labelPdf;
	@Column(name = "label_zpl")
	private String labelZpl;
	@Column(name = "packing_slipno")
	private String packingSlipno;
	@Column(name = "so_id")
	private String soId;
	@Column(name = "customer_id")
	private String customerId;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@ManyToOne
	@JoinColumn(name="id_user",referencedColumnName = "id")	
	private Users user;
	private String status;
	@Column(name = "ship_to")
	private String shipTo;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "part_id")
	private String part_id;
	@Column(name = "address")
	private String address;
	@Column(name = "batch_number")
	private String batchNumber;
	@Column(name = "customer_account")
	private String customerAccount;
	@Column(name = "extra_chargues")
	private Double extraChargues;
	@Column(name = "freight_desc")
	private String freightDesc;
	
	
		
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public Double getShipmentCost() {
		return shipmentCost;
	}
	public void setShipmentCost(Double shipmentCost) {
		this.shipmentCost = shipmentCost;
	}
	public Double getInsuranceCost() {
		return insuranceCost;
	}
	public void setInsuranceCost(Double insuranceCost) {
		this.insuranceCost = insuranceCost;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public Boolean getIsInternational() {
		return isInternational;
	}
	public void setIsInternational(Boolean isInternational) {
		this.isInternational = isInternational;
	}
	public Carrier getCarrier() {
		return carrier;
	}
	public void setCarrier(Carrier carrier) {
		this.carrier = carrier;
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
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
	public String getLabelPdf() {
		return labelPdf;
	}
	public void setLabelPdf(String labelPdf) {
		this.labelPdf = labelPdf;
	}
	public String getLabelZpl() {
		return labelZpl;
	}
	public void setLabelZpl(String labelZpl) {
		this.labelZpl = labelZpl;
	}
	public String getPackingSlipno() {
		return packingSlipno;
	}
	public void setPackingSlipno(String packingSlipno) {
		this.packingSlipno = packingSlipno;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getShipTo() {
		return shipTo;
	}
	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getPart_id() {
		return part_id;
	}
	public void setPart_id(String part_id) {
		this.part_id = part_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getCustomerAccount() {
		return customerAccount;
	}
	public void setCustomerAccount(String customerAccount) {
		this.customerAccount = customerAccount;
	}
	public Double getExtraChargues() {
		return extraChargues;
	}
	public void setExtraChargues(Double extraChargues) {
		this.extraChargues = extraChargues;
	}
	public String getFreightDesc() {
		return freightDesc;
	}
	public void setFreightDesc(String freightDesc) {
		this.freightDesc = freightDesc;
	}	
	
	
	
	
	
	

}
