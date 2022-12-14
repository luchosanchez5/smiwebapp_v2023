package com.request;

import java.io.Serializable;

public class LabelMessagesRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String reference1;
	private String reference2;
	private String reference3;
	
	public String getReference1() {
		return reference1;
	}
	
	public void setReference1(String reference1) {
		this.reference1 = reference1;
	}
	
	public String getReference2() {
		return reference2;
	}
	
	public void setReference2(String reference2) {
		this.reference2 = reference2;
	}
	
	public String getReference3() {
		return reference3;
	}
	
	public void setReference3(String reference3) {
		this.reference3 = reference3;
	}
	
	

}
