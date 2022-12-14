package com.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PackageTypeResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("package_id")
	private String packageId;
	@JsonProperty("package_code")
	private String packageCode;
	private String name;
	private DimensionResponse dimension;
	private String descriptions;
	
	
	
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(String descriptions) {
		this.descriptions = descriptions;
	}
	public DimensionResponse getDimension() {
		return dimension;
	}
	public void setDimension(DimensionResponse dimension) {
		this.dimension = dimension;
	}
	
	
	
	

}
