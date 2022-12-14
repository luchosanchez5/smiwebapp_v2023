package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="measure_unit")
public class MeasureUnit {
	
	@Id
	@Column(name = "id_measureunit")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idMeasureunit;
	@Column(name = "descmeasure_unit")
	private String descmeasureUnit;
	@Column(name = "stat_reg")
	private String statReg;
	
	
	
	
	public long getIdMeasureunit() {
		return idMeasureunit;
	}


	public void setIdMeasureunit(long idMeasureunit) {
		this.idMeasureunit = idMeasureunit;
	}


	public String getDescmeasureUnit() {
		return descmeasureUnit;
	}
	
	public void setDescmeasureUnit(String descmeasureUnit) {
		this.descmeasureUnit = descmeasureUnit;
	}
	
	public String getStatReg() {
		return statReg;
	}
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}
	
	
	

}
