package com.response;

import java.io.Serializable;

public class LabelDownloadResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pdf;
	private String png;
	private String zpl;
	private String href;
	
	
	public String getPdf() {
		return pdf;
	}
	
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	
	public String getPng() {
		return png;
	}
	
	public void setPng(String png) {
		this.png = png;
	}
	
	public String getZpl() {
		return zpl;
	}
	
	public void setZpl(String zpl) {
		this.zpl = zpl;
	}
	
	public String getHref() {
		return href;
	}
	
	public void setHref(String href) {
		this.href = href;
	}
	
	
	

}
