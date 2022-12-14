package com.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="material")
public class Material {
	
    @Id
    @Column(name = "id_material")
    @SequenceGenerator(name = "MAT_SEQ", sequenceName = "MAT_SEQ", initialValue = 1, allocationSize=100)
    @GeneratedValue(strategy = SEQUENCE, generator = "MAT_SEQ")
    
    private long idMaterial;
    @Column(name = "part_number")
    private String partNumber;
    @Column(name = "desc_material")
    private String descMaterial;
    @Column(name="Price", columnDefinition="numeric(10,4) default '00.0000'")
    private double unitCost;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
	@ManyToOne
	@JoinColumn(name="id_user",referencedColumnName = "id")		
	private Users user;	
	@ManyToOne
	@JoinColumn(name="id_measureunit",referencedColumnName = "id_measureunit")		
	private MeasureUnit measureUnit;
	@Column(name = "stat_reg")
    private String statReg;
	@Column(name = "typeM")    //1 = New  0 = Existing
	private String typeM;
    
    
    public Material () {
    	
    }
    
	public long getIdMaterial() {
		return idMaterial;
	}
	
	public void setIdMaterial(long idMaterial) {
		this.idMaterial = idMaterial;
	}
	
	
	
	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}
	
	

	public double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(double unitCost) {
		this.unitCost = unitCost;
	}

	public String getDescMaterial() {
		return descMaterial;
	}
	
	public void setDescMaterial(String descMaterial) {
		this.descMaterial = descMaterial;
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

	public String getTypeM() {
		return typeM;
	}

	public void setTypeM(String typeM) {
		this.typeM = typeM;
	}


	
	
    
    
    

}
