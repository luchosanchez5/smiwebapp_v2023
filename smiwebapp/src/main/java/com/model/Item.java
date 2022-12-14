package com.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//Quote Details
@Entity
@Table(name="item")
public class Item {
	
    @Id
    @Column(name = "id_item")
    @SequenceGenerator(name = "ITEM_SEQ", sequenceName = "ITEM_SEQ", initialValue = 1, allocationSize=1)
    @GeneratedValue(strategy = SEQUENCE, generator = "ITEM_SEQ")
    
    private long idItem;
    @Column(name = "item_number")
    private String itemNumber;   // Ex : A15130
	@ManyToOne
	@JoinColumn(name="id_quote",referencedColumnName = "id")		
	private Quote quote;
	@ManyToOne
	@JoinColumn(name="id_itemtype",referencedColumnName = "id")		
	private ItemType itemType ;	    
	@ManyToOne
	@JoinColumn(name="id_industry",referencedColumnName = "id")		
	private Industry industry ;	
	@ManyToOne
	@JoinColumn(name="id_itemstatus",referencedColumnName = "id")		
	private ItemStatus itemStatus;
	@ManyToOne
	@JoinColumn(name="id_teslaModel",referencedColumnName = "id")		
	private TeslaModel teslaModel;	
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@ManyToOne
	@JoinColumn(name="id_part",referencedColumnName = "id")		
	private Part part;
	@Column(name = "drawing_ava")
	private String drawingAva;
	@Column(name = "new_part")
	private String newPart;
	@Column(name = "sample_ava")
	private String sampleAva;
	@Column(name = "concerns_curr")
	private String concernsCurr;
	@Column(name = "cadile_ava")
	private String cadfileAva;
	@Column(name = "package_req")
	private String packageReq;
	@Column(name = "part_kiss_cut")
	private String partKissCut;
	@Column(name="cavities", columnDefinition="numeric(10) default '0'")
	private long cavities;
	@ManyToOne
	@JoinColumn(name="id_qitemtype",referencedColumnName = "id")		
	private QitemType qitemType;
	@Column(name = "anual_usage")
	private long anualUsage;
	@Column(name = "quantity")
	private long quantity;
	@Column(name = "suggest_mat")
	private String suggestMat;
	@Column(name = "suggest_vend")
	private String suggestVend;
	@Column(name = "target_price")
	private String targetPrice;
	@Column(name = "fob")
	private String fob;
	@ManyToOne
	@JoinColumn(name="id_user",referencedColumnName = "id")
	private Users users;
	@Column(name = "rfq_due_date")
	private LocalDateTime rfqdueDate;
	@Column(name = "stat_reg")
	private String statReg;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "item_material", joinColumns = @JoinColumn(name = "id_item"), inverseJoinColumns = @JoinColumn(name = "id_material"))
	private Set<Material> sugMaterials;
	
	//-- Cost Breakdown
	@Column(name = "material_cost")
	private double materialCost;
	@Column(name = "labor_cost")
	private double laborCost;
	@Column(name = "packaging_cost")
	private double packagingCost;
	@Column(name = "scrap_rate")
	private double scrapRate;
	@Column(name = "margin")
	private double margin;
	@Column(name = "tooling_cost")
	private double toolingCost;
	@Column(name = "smi_total_cost")
	private double smiTotalCost;
	@Column(name = "smi_sale_cost")
	private double smiSaleCost;
	@Column(name = "note")
	private String note;
	@ManyToOne
	@JoinColumn(name="id_measureunit",referencedColumnName = "id_measureunit")		
	private MeasureUnit measureUnit;
	
	@Column(name = "new_part_name")
	private String newPartName;
	@Column(name = "start_estimate")
	private String startEstimate;    // 0 = n  1 = yes
	@Column(name = "startestimate_date")
	private LocalDateTime startestimateDate;
	@Column(name = "last_update")
	private LocalDateTime lastUpdate;
	@Column(name = "date_deleted")
	private LocalDateTime dateDeleted;
	
	@ManyToOne
	@JoinColumn(name="user_deleted",referencedColumnName = "id")
	private Users userDeleted;
	@Column(name = "date_ordered")
	private LocalDateTime dateOrdered;	
	
	@Column(name = "po_number")
	private String poNumber;
	
	@Column(name = "po_amount", columnDefinition="double precision default 0.00")
	private Double poAmount;
	
	@Column(name = "date_register_ordered")
	private LocalDateTime dateRegisterOrdered;
	
	@ManyToOne
	@JoinColumn(name="user_register_ordered",referencedColumnName = "id")
	private Users userRegisterOrdered;
	
	@Column(name = "new_part_description")
	private String newPartDescription;	
	
	@ManyToOne
	@JoinColumn(name="id_incoterms",referencedColumnName = "id")		
	private Incoterms incoTerms;

	
	public long getIdItem() {
		return idItem;
	}

	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}

	public Set<Material> getSugMaterials() {
		return sugMaterials;
	}

	public void setSugMaterials(Set<Material> sugMaterials) {
		this.sugMaterials = sugMaterials;
	}

	public String getItemNumber() {
		return itemNumber;
	}
	
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	
	public ItemType getItemType() {
		return itemType;
	}
	
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	
	
	public Industry getIndustry() {
		return industry;
	}
	
	public void setIndustry(Industry industry) {
		this.industry = industry;
	}
	
	public ItemStatus getItemStatus() {
		return itemStatus;
	}
	
	public void setItemStatus(ItemStatus itemStatus) {
		this.itemStatus = itemStatus;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public Part getPart() {
		return part;
	}
	
	public void setPart(Part part) {
		this.part = part;
	}
	
	public String getDrawingAva() {
		return drawingAva;
	}
	
	public void setDrawingAva(String drawingAva) {
		this.drawingAva = drawingAva;
	}
	
	public String getNewPart() {
		return newPart;
	}
	
	public void setNewPart(String newPart) {
		this.newPart = newPart;
	}
	
	public String getSampleAva() {
		return sampleAva;
	}
	
	public void setSampleAva(String sampleAva) {
		this.sampleAva = sampleAva;
	}
	
	public String getConcernsCurr() {
		return concernsCurr;
	}
	
	public void setConcernsCurr(String concernsCurr) {
		this.concernsCurr = concernsCurr;
	}
	
	public String getCadfileAva() {
		return cadfileAva;
	}
	
	public void setCadfileAva(String cadfileAva) {
		this.cadfileAva = cadfileAva;
	}
	
	public String getPackageReq() {
		return packageReq;
	}
	
	public void setPackageReq(String packageReq) {
		this.packageReq = packageReq;
	}
	
	public String getPartKissCut() {
		return partKissCut;
	}
	
	public void setPartKissCut(String partKissCut) {
		this.partKissCut = partKissCut;
	}
	
	public QitemType getQitemType() {
		return qitemType;
	}
	
	public void setQitemType(QitemType qitemType) {
		this.qitemType = qitemType;
	
	}
	
	public long getAnualUsage() {
		return anualUsage;
	}
	
	public void setAnualUsage(long anualUsage) {
		this.anualUsage = anualUsage;
	}
	
	public long getQuantity() {
		return quantity;
	}
	
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	
	public String getSuggestMat() {
		return suggestMat;
	}
	
	public void setSuggestMat(String suggestMat) {
		this.suggestMat = suggestMat;
	}
	
	public String getSuggestVend() {
		return suggestVend;
	}
	
	public void setSuggestVend(String suggestVend) {
		this.suggestVend = suggestVend;
	}
	
	public String getTargetPrice() {
		return targetPrice;
	}
	
	public void setTargetPrice(String targetPrice) {
		this.targetPrice = targetPrice;
	}
	
	public String getFob() {
		return fob;
	}
	
	public void setFob(String fob) {
		this.fob = fob;
	}
	
	public Users getUsers() {
		return users;
	}
	
	public void setUsers(Users users) {
		this.users = users;
	}
	
	public LocalDateTime getRfqdueDate() {
		return rfqdueDate;
	}
	
	public void setRfqdueDate(LocalDateTime rfqdueDate) {
		this.rfqdueDate = rfqdueDate;
	}
	
	public String getStatReg() {
		return statReg;
	}
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}

	public double getMaterialCost() {
		return materialCost;
	}

	public void setMaterialCost(double materialCost) {
		this.materialCost = materialCost;
	}

	public double getLaborCost() {
		return laborCost;
	}

	public void setLaborCost(double laborCost) {
		this.laborCost = laborCost;
	}

	public double getPackagingCost() {
		return packagingCost;
	}

	public void setPackagingCost(double packagingCost) {
		this.packagingCost = packagingCost;
	}

	public double getScrapRate() {
		return scrapRate;
	}

	public void setScrapRate(double scrapRate) {
		this.scrapRate = scrapRate;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public double getToolingCost() {
		return toolingCost;
	}

	public void setToolingCost(double toolingCost) {
		this.toolingCost = toolingCost;
	}

	public double getSmiTotalCost() {
		return smiTotalCost;
	}

	public void setSmiTotalCost(double smiTotalCost) {
		this.smiTotalCost = smiTotalCost;
	}

	public double getSmiSaleCost() {
		return smiSaleCost;
	}

	public void setSmiSaleCost(double smiSaleCost) {
		this.smiSaleCost = smiSaleCost;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public MeasureUnit getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(MeasureUnit measureUnit) {
		this.measureUnit = measureUnit;
	}

	public String getNewPartName() {
		return newPartName;
	}

	public void setNewPartName(String newPartName) {
		this.newPartName = newPartName;
	}

	public long getCavities() {
		return cavities;
	}

	public void setCavities(long cavities) {
		this.cavities = cavities;
	}

	public String getStartEstimate() {
		return startEstimate;
	}

	public void setStartEstimate(String startEstimate) {
		this.startEstimate = startEstimate;
	}

	public LocalDateTime getStartestimateDate() {
		return startestimateDate;
	}

	public void setStartestimateDate(LocalDateTime startestimateDate) {
		this.startestimateDate = startestimateDate;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public LocalDateTime getDateDeleted() {
		return dateDeleted;
	}

	public void setDateDeleted(LocalDateTime dateDeleted) {
		this.dateDeleted = dateDeleted;
	}

	public Users getUserDeleted() {
		return userDeleted;
	}

	public void setUserDeleted(Users userDeleted) {
		this.userDeleted = userDeleted;
	}

	public TeslaModel getTeslaModel() {
		return teslaModel;
	}

	public void setTeslaModel(TeslaModel teslaModel) {
		this.teslaModel = teslaModel;
	}

	public LocalDateTime getDateOrdered() {
		return dateOrdered;
	}

	public void setDateOrdered(LocalDateTime dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}

	public Double getPoAmount() {
		return poAmount;
	}

	public void setPoAmount(Double poAmount) {
		this.poAmount = poAmount;
	}

	public LocalDateTime getDateRegisterOrdered() {
		return dateRegisterOrdered;
	}

	public void setDateRegisterOrdered(LocalDateTime dateRegisterOrdered) {
		this.dateRegisterOrdered = dateRegisterOrdered;
	}

	public Users getUserRegisterOrdered() {
		return userRegisterOrdered;
	}

	public void setUserRegisterOrdered(Users userRegisterOrdered) {
		this.userRegisterOrdered = userRegisterOrdered;
	}

	public String getNewPartDescription() {
		return newPartDescription;
	}

	public void setNewPartDescription(String newPartDescription) {
		this.newPartDescription = newPartDescription;
	}

	public Incoterms getIncoTerms() {
		return incoTerms;
	}

	public void setIncoTerms(Incoterms incoTerms) {
		this.incoTerms = incoTerms;
	}

	
	
}
