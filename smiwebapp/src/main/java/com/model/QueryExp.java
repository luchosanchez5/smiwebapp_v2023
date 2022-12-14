package com.model;

import javax.persistence.Column;
import javax.persistence.Id;

public class QueryExp {
	

	private String termsCode;
	private String termsDesc;
	private int discountDays;	
	private  int dueDays;
	
	
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
	
	
	
	
	

}
