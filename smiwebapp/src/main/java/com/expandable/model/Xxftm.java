package com.expandable.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="XXFTM")
public class Xxftm {
	

	
	@Id
	@Column(name = "TERMS_CODE", columnDefinition="nchar(2)", nullable=false)
	private String termsCode;
	
	@Column(name = "TERMS_DESC", columnDefinition="nvarchar(24)", nullable=false)
	private String termsDesc;
	
	@Column(name = "DISCOUNT_TYPE", columnDefinition="nchar(1)", nullable=false)
	private String discountType;
	
	
	@Column(name = "DISCOUNT_PCT", columnDefinition="decimal(5,2)", nullable=false)
	private double discountPct;
	
	@Column(name = "DISCOUNT_CALC", columnDefinition="nchar(1)", nullable=false)
	private String discountCalc;	
	
	@Column(name = "DISCOUNT_DAYS", columnDefinition="decimal(4,0)", nullable=false)
	private int discountDays;	
	
	@Column(name = "DUE_DAYS", columnDefinition="decimal(4,0)", nullable=false)
	private  int dueDays;	
	
	@Column(name = "DAY_OF_MONTH", columnDefinition="decimal(4,0)", nullable=false)
	private int dayOfMonth;
	
	@Column(name = "USER_FIELD_4", columnDefinition="nchar(4)", nullable=false)
	private String userfield4;	
	
	@Column(name = "CREATED_BY", columnDefinition="nchar(8)", nullable=false)
	private String createdby;
		
	@Column(name = "DATE_CREATED", columnDefinition="datetime", nullable=true)
	private LocalDateTime dateCreated;
	
	@Column(name = "MODIFIED_BY", columnDefinition="nchar(8)", nullable=false)
	private String modifiedBy;
	
	@Column(name = "DATE_MODIFIED", columnDefinition="datetime", nullable=true)
	private LocalDateTime dateModified;
		
	@Column(name = "DATE_LAST_UPDT", columnDefinition="datetime", nullable=false)
	private LocalDateTime dateLastUpdate;
	
	
	@Column(name = "DELETE_FLAG", columnDefinition="nchar(1)", nullable=false)
	private String deleteFlag;
	

    
	

	public Xxftm() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Xxftm(String termsCode, String termsDesc, String discountType, double discountPct, String discountCalc,
			int discountDays, int dueDays, int dayOfMonth, String userfield4, String createdby,
			LocalDateTime dateCreated, String modifiedBy, LocalDateTime dateModified, LocalDateTime dateLastUpdate,
			String deleteFlag) {
		super();
		this.termsCode = termsCode;
		this.termsDesc = termsDesc;
		this.discountType = discountType;
		this.discountPct = discountPct;
		this.discountCalc = discountCalc;
		this.discountDays = discountDays;
		this.dueDays = dueDays;
		this.dayOfMonth = dayOfMonth;
		this.userfield4 = userfield4;
		this.createdby = createdby;
		this.dateCreated = dateCreated;
		this.modifiedBy = modifiedBy;
		this.dateModified = dateModified;
		this.dateLastUpdate = dateLastUpdate;
		this.deleteFlag = deleteFlag;
	}




	public String getTermsCode() {
		return termsCode;
	}


	public void setTermsCode(String termsCode) {
		this.termsCode = termsCode;
	}


	public String getTermsDesc() {
		return termsDesc;
	}


	public void setTermsDesc(String termsDesc) {
		this.termsDesc = termsDesc;
	}


	public String getDiscountType() {
		return discountType;
	}


	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}


	public double getDiscountPct() {
		return discountPct;
	}


	public void setDiscountPct(double discountPct) {
		this.discountPct = discountPct;
	}


	public String getDiscountCalc() {
		return discountCalc;
	}


	public void setDiscountCalc(String discountCalc) {
		this.discountCalc = discountCalc;
	}


	public int getDiscountDays() {
		return discountDays;
	}


	public void setDiscountDays(int discountDays) {
		this.discountDays = discountDays;
	}


	public int getDueDays() {
		return dueDays;
	}


	public void setDueDays(int dueDays) {
		this.dueDays = dueDays;
	}


	public int getDayOfMonth() {
		return dayOfMonth;
	}


	public void setDayOfMonth(int dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}


	public String getUserfield4() {
		return userfield4;
	}


	public void setUserfield4(String userfield4) {
		this.userfield4 = userfield4;
	}


	public String getCreatedby() {
		return createdby;
	}


	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}


	public LocalDateTime getDateCreated() {
		return dateCreated;
	}


	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public LocalDateTime getDateModified() {
		return dateModified;
	}


	public void setDateModified(LocalDateTime dateModified) {
		this.dateModified = dateModified;
	}


	public LocalDateTime getDateLastUpdate() {
		return dateLastUpdate;
	}


	public void setDateLastUpdate(LocalDateTime dateLastUpdate) {
		this.dateLastUpdate = dateLastUpdate;
	}


	public String getDeleteFlag() {
		return deleteFlag;
	}


	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	
	
	
	
	
	
	
	
	

}
