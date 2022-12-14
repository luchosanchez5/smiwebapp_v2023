package com.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressRequest implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	@JsonProperty("company_name")
	private String companyName;
	@JsonProperty("phone")
	private String phone;
	@JsonProperty("address_line1")
	private String addressLine1;
	@JsonProperty("address_line2")
	private String addressLine2;
	@JsonProperty("address_line3")
	private String addressLine3;
	@JsonProperty("city_locality")
	private String cityLoyality;
	@JsonProperty("state_province")
	private String stateProvince;
	@JsonProperty("postal_code")
	private String postalCode;
	@JsonProperty("country_code")
	private String countryCode;
	@JsonProperty("address_residential_indicator")
	private String addressResidential;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public String getCityLoyality() {
		return cityLoyality;
	}
	public void setCityLoyality(String cityLoyality) {
		this.cityLoyality = cityLoyality;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getAddressResidential() {
		return addressResidential;
	}
	public void setAddressResidential(String addressResidential) {
		this.addressResidential = addressResidential;
	}
	
	
	
	
	
	
	
	
	
}
