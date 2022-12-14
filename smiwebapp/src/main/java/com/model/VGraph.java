package com.model;

import java.util.List;

public class VGraph {
	
	private long year;
    private long month;
    private String str_month;
    private List<String> list_str_month;
    private List<Long> list_int_month;
    private long tot_quotes;
    private long tot_quotes_ordered;
    private List<Long> list_tot_quotes;
    private List<Long> list_tot_quotes_ord;
    private List<Double> list_po_amount;
    
    
	public long getYear() {
		return year;
	}
	
	public void setYear(long year) {
		this.year = year;
	}
	
	public long getMonth() {
		return month;
	}
	
	public void setMonth(long month) {
		this.month = month;
	}
	
	public String getStr_month() {
		return str_month;
	}
	
	public void setStr_month(String str_month) {
		this.str_month = str_month;
	}
	
	public List<String> getList_str_month() {
		return list_str_month;
	}
	
	public void setList_str_month(List<String> list_str_month) {
		this.list_str_month = list_str_month;
	}
	
	public List<Long> getList_int_month() {
		return list_int_month;
	}
	
	public void setList_int_month(List<Long> list_int_month) {
		this.list_int_month = list_int_month;
	}
	
	public long getTot_quotes() {
		return tot_quotes;
	}
	
	public void setTot_quotes(long tot_quotes) {
		this.tot_quotes = tot_quotes;
	}
	
	public long getTot_quotes_ordered() {
		return tot_quotes_ordered;
	}
	
	public void setTot_quotes_ordered(long tot_quotes_ordered) {
		this.tot_quotes_ordered = tot_quotes_ordered;
	}

	public List<Long> getList_tot_quotes() {
		return list_tot_quotes;
	}

	public void setList_tot_quotes(List<Long> list_tot_quotes) {
		this.list_tot_quotes = list_tot_quotes;
	}

	public List<Long> getList_tot_quotes_ord() {
		return list_tot_quotes_ord;
	}

	public void setList_tot_quotes_ord(List<Long> list_tot_quotes_ord) {
		this.list_tot_quotes_ord = list_tot_quotes_ord;
	}

	public List<Double> getList_po_amount() {
		return list_po_amount;
	}

	public void setList_po_amount(List<Double> list_po_amount) {
		this.list_po_amount = list_po_amount;
	}
    
    
	
    
}    