package com.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarrierResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("carrier_id")
	private String carrierId;
	@JsonProperty("carrier_code")
	private String carrierCode;
	@JsonProperty("account_number")
	private String accountNumber;
	@JsonProperty("requires_funded_amount")
	private Boolean requiresFundedAmount;
	private Double balance;
	private String nickname;
	private Boolean primary;
	@JsonProperty("has_multi_package_supporting_services")
	private Boolean hasMultiPackageSupportingServices;
	private List<ServiceResponse> services;
	private List<PackageTypeResponse> packages;
	private List<OptionResponse> options;
	
	
	
	

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
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	
	
	public Boolean getRequiresFundedAmount() {
		return requiresFundedAmount;
	}
	public void setRequiresFundedAmount(Boolean requiresFundedAmount) {
		this.requiresFundedAmount = requiresFundedAmount;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Boolean getPrimary() {
		return primary;
	}
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}
	public Boolean getHasMultiPackageSupportingServices() {
		return hasMultiPackageSupportingServices;
	}
	public void setHasMultiPackageSupportingServices(Boolean hasMultiPackageSupportingServices) {
		this.hasMultiPackageSupportingServices = hasMultiPackageSupportingServices;
	}
	public List<ServiceResponse> getServices() {
		return services;
	}
	public void setServices(List<ServiceResponse> services) {
		this.services = services;
	}
	public List<PackageTypeResponse> getPackages() {
		return packages;
	}
	public void setPackages(List<PackageTypeResponse> packages) {
		this.packages = packages;
	}
	public List<OptionResponse> getOptions() {
		return options;
	}
	public void setOptions(List<OptionResponse> options) {
		this.options = options;
	}
	
	

	
	
}
