package com.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="temp_item")
public class TempItem {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "session_id")
    private String sessionId;
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
	@Column(name = "cadfile_ava")
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
	@Column(name = "rfqdue_date")
	private LocalDateTime rfqdueDate;
	@Column(name = "stat_reg")
	private String statReg;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tempitem_material", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "id_material"))
	private Set<Material> sugMaterials;
	@Column(name = "new_part_name")
	private String newPartName;	
	@Column(name = "note")
	private String note;
	
	@ManyToOne
	@JoinColumn(name="id_measureunit",referencedColumnName = "id_measureunit")		
	private MeasureUnit measureUnit;
	
	@ManyToOne
	@JoinColumn(name="id_incoterms",referencedColumnName = "id")		
	private Incoterms incoTerms;
	
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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

	public Set<Material> getSugMaterials() {
		return sugMaterials;
	}

	public void setSugMaterials(Set<Material> sugMaterials) {
		this.sugMaterials = sugMaterials;
	}

	public String getStatReg() {
		return statReg;
	}

	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}

	public MeasureUnit getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(MeasureUnit measureUnit) {
		this.measureUnit = measureUnit;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public TeslaModel getTeslaModel() {
		return teslaModel;
	}

	public void setTeslaModel(TeslaModel teslaModel) {
		this.teslaModel = teslaModel;
	}

	public Incoterms getIncoTerms() {
		return incoTerms;
	}

	public void setIncoTerms(Incoterms incoTerms) {
		this.incoTerms = incoTerms;
	}
	
	
	 
	
	
	


}
