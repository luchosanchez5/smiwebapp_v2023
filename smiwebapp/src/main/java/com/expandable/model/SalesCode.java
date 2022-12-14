package com.expandable.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="v_app_sales_code")
public class SalesCode {
	
	@Id
	@Column(name = "VALUE", columnDefinition="nchar(4)", nullable=false)
	private String value;
	
	@Column(name = "DESCRIPTION", columnDefinition="nvarchar(32)", nullable=false)
	private String description;
	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	
	
	

}
