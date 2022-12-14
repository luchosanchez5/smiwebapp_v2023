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
@Table(name="mat_details")
public class MatDetails {
	
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "MATITEM_SEQ", sequenceName = "MATITEM_SEQ", initialValue = 1, allocationSize=100)
    @GeneratedValue(strategy = SEQUENCE, generator = "MATITEM_SEQ")
    
    private long id;
	@ManyToOne
	@JoinColumn(name="id_material",referencedColumnName = "id_material")		
	private Material material;
	@ManyToOne
	@JoinColumn(name="id_item",referencedColumnName = "id_item")		
	private Item item;
	@ManyToOne
	@JoinColumn(name="id_measureunit",referencedColumnName = "id_measureunit")		
	private MeasureUnit measureUnit;
	@Column(name = "mat_widht")
	private double matWidht;
	@Column(name = "cost_um")
	private double costUm;
	@Column(name = "lf")
	private double lf;
	@Column(name = "progression")
	private double progression;
	@Column(name = "slit_width")
	private double slitWidth;
	@Column(name = "nro_rolls")
	private double nroRolls;
	@Column(name = "yield")
	private double yield;
	@Column(name = "price_each")
	private double priceEach;
	@Column(name = "freight")
	private double freight;
	@Column(name = "total_cost")
	private double totalCost;
	@ManyToOne
	@JoinColumn(name="id_tool",referencedColumnName = "id")		
	private Tool tool;	
	@ManyToOne
	@JoinColumn(name="id_machine",referencedColumnName = "id")		
	private Machine machine;	
    @ManyToOne
	@JoinColumn(name="id_user",referencedColumnName = "id")	
    private Users user;
    @Column(name = "qty_hour")
    private long qtyHour;
    @Column(name = "labor_rate")
    private double laborRate;
    @Column(name="labor_cost")
    private double laborCost;
    @Column(name = "step_description")
    private String stepDescription;
    @Column(name = "stat_reg")
    private String statReg;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "delete_date")
    private LocalDateTime deleteDate;
    @ManyToOne
	@JoinColumn(name="delete_user",referencedColumnName = "id")	
    private Users deleteUser;	
    @Column(name = "type_detail")
    private String typeDetail;
    @Column(name = "typeMaterial")   //    Yes = 1    -   No = 0
    private String typeMaterial;
    
	@Column(name = "freight_cost")
	private double freightCost;
	
	@Column(name = "mat_description")
	private double matDescription;
	
	@Column(name = "notesMat")
	private String notesMat;
    
    
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public Item getItem() {
		return item;
	}
	
	public void setItem(Item item) {
		this.item = item;
	}
	
	public double getMatWidht() {
		return matWidht;
	}
	
	public void setMatWidht(double matWidht) {
		this.matWidht = matWidht;
	}
	
	public double getCostUm() {
		return costUm;
	}
	
	public void setCostUm(double costUm) {
		this.costUm = costUm;
	}
	
	public double getLf() {
		return lf;
	}
	
	public void setLf(double lf) {
		this.lf = lf;
	}
	
	public double getProgression() {
		return progression;
	}
	
	public void setProgression(double progression) {
		this.progression = progression;
	}
	
	public double getSlitWidth() {
		return slitWidth;
	}
	
	public void setSlitWidth(double slitWidth) {
		this.slitWidth = slitWidth;
	}
	
	public double getNroRolls() {
		return nroRolls;
	}
	
	public void setNroRolls(double nroRolls) {
		this.nroRolls = nroRolls;
	}
	
	public double getYield() {
		return yield;
	}
	
	public void setYield(double yield) {
		this.yield = yield;
	}
	
	public double getPriceEach() {
		return priceEach;
	}
	
	public void setPriceEach(double priceEach) {
		this.priceEach = priceEach;
	}
	
	public double getFreight() {
		return freight;
	}
	
	public void setFreight(double freight) {
		this.freight = freight;
	}
	
	public double getTotalCost() {
		return totalCost;
	}
	
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public Tool getTool() {
		return tool;
	}
	
	public void setTool(Tool tool) {
		this.tool = tool;
	}
	
	public Machine getMachine() {
		return machine;
	}
	
	public void setMachine(Machine machine) {
		this.machine = machine;
	}
	
	public long getQtyHour() {
		return qtyHour;
	}
	
	public void setQtyHour(long qtyHour) {
		this.qtyHour = qtyHour;
	}
	
	public double getLaborRate() {
		return laborRate;
	}
	
	public void setLaborRate(double laborRate) {
		this.laborRate = laborRate;
	}
	
	public double getLaborCost() {
		return laborCost;
	}
	
	public void setLaborCost(double laborCost) {
		this.laborCost = laborCost;
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

	public String getStepDescription() {
		return stepDescription;
	}

	public void setStepDescription(String stepDescription) {
		this.stepDescription = stepDescription;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(LocalDateTime deleteDate) {
		this.deleteDate = deleteDate;
	}

	public Users getDeleteUser() {
		return deleteUser;
	}

	public void setDeleteUser(Users deleteUser) {
		this.deleteUser = deleteUser;
	}

	public String getTypeDetail() {
		return typeDetail;
	}

	public void setTypeDetail(String typeDetail) {
		this.typeDetail = typeDetail;
	}

	public String getTypeMaterial() {
		return typeMaterial;
	}

	public void setTypeMaterial(String typeMaterial) {
		this.typeMaterial = typeMaterial;
	}

	public double getFreightCost() {
		return freightCost;
	}

	public void setFreightCost(double freightCost) {
		this.freightCost = freightCost;
	}

	public double getMatDescription() {
		return matDescription;
	}

	public void setMatDescription(double matDescription) {
		this.matDescription = matDescription;
	}

	public String getNotesMat() {
		return notesMat;
	}

	public void setNotesMat(String notesMat) {
		this.notesMat = notesMat;
	}



    
    
	
    
    

}
