package com.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="part")
public class Part {
	
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "PART_SEQ", sequenceName = "PART_SEQ", initialValue = 1, allocationSize=100)
    @GeneratedValue(strategy = SEQUENCE, generator = "PART_SEQ")
    private long id;
    @Column(name = "part_code")
    private String partCode;
    @Column(name = "part_number")
    private String partNumber;
    @Column(name = "part_desc")
    private String partDesc;
    @Column(name = "part_cost")
    private long partCost;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_user",referencedColumnName = "id")		
	private Users user;	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_measureunit",referencedColumnName = "id_measureunit")		
	private MeasureUnit measureUnit;
	@Column(name = "stat_reg")
    private String statReg;
    
    
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getPartCode() {
		return partCode;
	}
	
	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
	
	public String getPartNumber() {
		return partNumber;
	}
	
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	public String getPartDesc() {
		return partDesc;
	}
	
	public void setPartDesc(String partDesc) {
		this.partDesc = partDesc;
	}
	
	public long getPartCost() {
		return partCost;
	}
	
	public void setPartCost(long partCost) {
		this.partCost = partCost;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public Users getUser() {
		return user;
	}
	
	public void setUser(Users user) {
		this.user = user;
	}
	
	
	public MeasureUnit getMeasureUnit() {
		return measureUnit;
	}
	
	public void setMeasureUnit(MeasureUnit measureUnit) {
		this.measureUnit = measureUnit;
	}
	
	public String getStatReg() {
		return statReg;
	}
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}
	
}
