package com.expandable.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SOFST")
public class Sofst {
	
	@Id
	@Column(name = "RECNUM", columnDefinition="decimal", nullable=false)
	private Double recNum;
	
	@Column(name = "SO_ID", columnDefinition="nchar(8)", nullable=false)
	private String soId;

	@Column(name = "SO_LINE_NO", columnDefinition="decimal(6,2)", nullable=false)
	private Double soLine;

	@Column(name = "ACTION_DATE", columnDefinition="datetime", nullable=false)
	private LocalDateTime actionDate;
	
	@Column(name = "ACTION_TYPE", columnDefinition="nchar(2)", nullable=false)
	private String actionType;
	
	@Column(name = "BATCH_NUMBER", columnDefinition="decimal(6,0)", nullable=false)
	private Double batchNumber;
	
	@Column(name = "SHIPMENT_NO", columnDefinition="decimal(4,0)", nullable=false)
	private Double shipmentNo;
	
	@Column(name = "GL_YEAR", columnDefinition="nchar(4)", nullable=false)
	private String glYear;
	
	@Column(name = "GL_MONTH", columnDefinition="decimal(2,0)", nullable=false)
	private Double glMonth;
	
	@Column(name = "REFERENCE", columnDefinition="nvarchar(16)", nullable=false)
	private String reference;
	
	@Column(name = "LOT_ID", columnDefinition="nchar(16)", nullable=false)
	private String lotId;
	
	@Column(name = "STORES_CODE", columnDefinition="nchar(2)", nullable=false)
	private String storesCode;
	
	@Column(name = "PART_ID", columnDefinition="nchar(25)", nullable=false)
	private String partId;
	
	@Column(name = "PART_UM", columnDefinition="nchar(2)", nullable=false)
	private String partUm;
	
	@Column(name = "SO_UM", columnDefinition="nchar(2)", nullable=false)
	private String soUM;
	
	@Column(name = "DWG_REV", columnDefinition="nchar(4)", nullable=false)
	private String dwgRev;
	
	@Column(name = "ECN", columnDefinition="nchar(6)", nullable=false)
	private String ecn;
	
	@Column(name = "STOCK_LOCATION", columnDefinition="nchar(16)", nullable=false)
	private String stockLocation;
	
	@Column(name = "SHIP_TYPE", columnDefinition="nchar(1)", nullable=false)
	private String shipType;
	
	@Column(name = "COMPANY_ID", columnDefinition="nchar(2)", nullable=false)
	private String companyId;
	
	@Column(name = "DEPARTMENT", columnDefinition="nchar(4)", nullable=false)
	private String departament;
	
	@Column(name = "ACCOUNT", columnDefinition="nchar(8)", nullable=false)
	private String account;
	
	@Column(name = "JOB_ID", columnDefinition="nchar(8)", nullable=false)
	private String jobId;
	
	@Column(name = "OPER_CODE", columnDefinition="decimal(4,0)", nullable=false)
	private Double operCode;
	
	@Column(name = "QUANTITY", columnDefinition="decimal(14,4)", nullable=false)
	private Double quantity;
	
	@Column(name = "INV_QUANTITY", columnDefinition="decimal(14,4)", nullable=false)
	private Double invQuantity;
	
	@Column(name = "COST_AMT", columnDefinition="decimal(14,2)", nullable=false)
	private Double costAmt;
	
	@Column(name = "AMOUNT", columnDefinition="decimal(14,2)", nullable=false)
	private Double amount;
	
	@Column(name = "LC_AMOUNT", columnDefinition="decimal(14,2)", nullable=false)
	private Double lcAmount;
	
	@Column(name = "REASON_CODE", columnDefinition="nchar(2)", nullable=false)
	private String reasonCode;
	
	@Column(name = "ORDER_DIFF", columnDefinition="decimal(14,2)", nullable=false)
	private Double orderDiff;
	
	@Column(name = "INVOICE_NUMBER", columnDefinition="decimal(7,0)", nullable=false)
	private Double invoiceNumber;
	
	@Column(name = "INV_LINE_NO", columnDefinition="decimal(4,0)", nullable=false)
	private Double invlineNo;	
	
	@Column(name = "INVOICE_DATE", columnDefinition="datetime", nullable=false)
	private LocalDateTime invoiceDate;
	
	@Column(name = "SALE_CONV", columnDefinition="decimal(10,4)", nullable=false)
	private Double saleConv;
	
	@Column(name = "SALE_DEC", columnDefinition="decimal(2,0)", nullable=false)
	private Double saleDec;
	
	@Column(name = "PROGRAM_ID", columnDefinition="nchar(6)", nullable=false)
	private String programId;
	
	@Column(name = "REPORT_FLAG", columnDefinition="nchar(1)", nullable=false)
	private String reportFlag;
			
	@Column(name = "BILL_OF_LADING", columnDefinition="nvarchar(48)", nullable=false)
	private String billofLanding;
	
	@Column(name = "SHIP_VIA", columnDefinition="nvarchar(16)", nullable=false)
	private String shipVia;
	
	@Column(name = "WEIGHT", columnDefinition="decimal(10,4)", nullable=false)
	private Double weight;
	
	@Column(name = "NUMBER_CARTONS", columnDefinition="decimal(4,0)", nullable=false)
	private Double numberCartons;
	
	@Column(name = "ACTUAL_COST", columnDefinition="decimal(14,2)", nullable=false)
	private Double actualCost;
	
	@Column(name = "TO_STORES", columnDefinition="nchar(1)", nullable=false)
	private String toStores;
	
	@Column(name = "TO_LOCATION", columnDefinition="nvarchar(16)", nullable=false)
	private String toLocation;
	
	@Column(name = "SO_CURR_RATE", columnDefinition="decimal(8,4)", nullable=false)
	private Double socurrRate;
	
	@Column(name = "AR_CURR_RATE", columnDefinition="decimal(8,4)", nullable=false)
	private Double arcurrRate;
	
	@Column(name = "SO_BC_RATE", columnDefinition="decimal(8,4)", nullable=false)
	private Double sobcRate;
	
	@Column(name = "AR_BC_RATE", columnDefinition="decimal(8,4)", nullable=false)
	private Double arbcRate;
	
	@Column(name = "PACK_LIST_FLAG", columnDefinition="nchar(1)", nullable=false)
	private String packlistFlag;
	
	@Column(name = "DATE_LAST_EDI", columnDefinition="datetime", nullable=false)
	private LocalDateTime datelastEdi;
	
	@Column(name = "PACK_SLIP_DATE", columnDefinition="datetime", nullable=false)
	private LocalDateTime packslipDate;
		
	@Column(name = "SOFST_USER_1", columnDefinition="nvarchar(24)", nullable=false)
	private String sofstUser1;
	
	@Column(name = "SOFST_USER_2", columnDefinition="nvarchar(24)", nullable=false)
	private String sofstUser2;
	
	@Column(name = "REQ_SHIP_DATE", columnDefinition="datetime", nullable=false)
	private LocalDateTime reqshipDate;
	
	@Column(name = "SCH_SHIP_DATE", columnDefinition="datetime", nullable=false)
	private LocalDateTime shshipDate;
	
	@Column(name = "REV_SHIP_DATE", columnDefinition="datetime", nullable=false)
	private LocalDateTime revshipDate;
	
	@Column(name = "BOX_NUMBER", columnDefinition="decimal(4,0)", nullable=false)
	private Double boxNumber;
	
	@Column(name = "TOTAL_BOXES", columnDefinition="decimal(4,0)", nullable=false)
	private Double totalBoxes;
	
	@Column(name = "PACK_SHIP_ID", columnDefinition="nchar(12)", nullable=false)
	private String packshipId;
	
	@Column(name = "LOCK_STATUS", columnDefinition="nvarchar(100)", nullable=false)
	private String lockStatus;
		
	@Column(name = "OPERATOR_ID", columnDefinition="nchar(8)", nullable=false)
	private String operatorId;
	
	@Column(name = "CREATED_BY", columnDefinition="nchar(8)", nullable=false)
	private String createdBy;
	
	@Column(name = "DATE_CREATED", columnDefinition="datetime", nullable=false)
	private LocalDateTime dateCreated;
	
	@Column(name = "MODIFIED_BY", columnDefinition="nchar(8)", nullable=false)
	private String modifiedBy;
	
	@Column(name = "DATE_MODIFIED", columnDefinition="datetime", nullable=false)
	private LocalDateTime dateModified;
	
	@Column(name = "TIME_LAST_UPDT", columnDefinition="nchar(5)", nullable=false)
	private String timelastUpdt;
	
	@Column(name = "DATE_LAST_UPDT", columnDefinition="datetime", nullable=false)
	private LocalDateTime datelastUpdate;
	
		
	
	
	
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}
	
	
	public Double getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(Double batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getBillofLanding() {
		return billofLanding;
	}

	public void setBillofLanding(String billofLanding) {
		this.billofLanding = billofLanding;
	}

	public Double getRecNum() {
		return recNum;
	}

	public void setRecNum(Double recNum) {
		this.recNum = recNum;
	}

	public LocalDateTime getActionDate() {
		return actionDate;
	}

	public void setActionDate(LocalDateTime actionDate) {
		this.actionDate = actionDate;
	}

	public Double getSoLine() {
		return soLine;
	}

	public void setSoLine(Double soLine) {
		this.soLine = soLine;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Double getShipmentNo() {
		return shipmentNo;
	}

	public void setShipmentNo(Double shipmentNo) {
		this.shipmentNo = shipmentNo;
	}

	public String getGlYear() {
		return glYear;
	}

	public void setGlYear(String glYear) {
		this.glYear = glYear;
	}

	public Double getGlMonth() {
		return glMonth;
	}

	public void setGlMonth(Double glMonth) {
		this.glMonth = glMonth;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}

	public String getStoresCode() {
		return storesCode;
	}

	public void setStoresCode(String storesCode) {
		this.storesCode = storesCode;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getPartUm() {
		return partUm;
	}

	public void setPartUm(String partUm) {
		this.partUm = partUm;
	}

	public String getSoUM() {
		return soUM;
	}

	public void setSoUM(String soUM) {
		this.soUM = soUM;
	}

	public String getDwgRev() {
		return dwgRev;
	}

	public void setDwgRev(String dwgRev) {
		this.dwgRev = dwgRev;
	}

	public String getEcn() {
		return ecn;
	}

	public void setEcn(String ecn) {
		this.ecn = ecn;
	}

	public String getStockLocation() {
		return stockLocation;
	}

	public void setStockLocation(String stockLocation) {
		this.stockLocation = stockLocation;
	}

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getDepartament() {
		return departament;
	}

	public void setDepartament(String departament) {
		this.departament = departament;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public Double getOperCode() {
		return operCode;
	}

	public void setOperCode(Double operCode) {
		this.operCode = operCode;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getInvQuantity() {
		return invQuantity;
	}

	public void setInvQuantity(Double invQuantity) {
		this.invQuantity = invQuantity;
	}

	public Double getCostAmt() {
		return costAmt;
	}

	public void setCostAmt(Double costAmt) {
		this.costAmt = costAmt;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getLcAmount() {
		return lcAmount;
	}

	public void setLcAmount(Double lcAmount) {
		this.lcAmount = lcAmount;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public Double getOrderDiff() {
		return orderDiff;
	}

	public void setOrderDiff(Double orderDiff) {
		this.orderDiff = orderDiff;
	}

	public Double getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Double invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Double getInvlineNo() {
		return invlineNo;
	}

	public void setInvlineNo(Double invlineNo) {
		this.invlineNo = invlineNo;
	}

	public LocalDateTime getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDateTime invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Double getSaleConv() {
		return saleConv;
	}

	public void setSaleConv(Double saleConv) {
		this.saleConv = saleConv;
	}

	public Double getSaleDec() {
		return saleDec;
	}

	public void setSaleDec(Double saleDec) {
		this.saleDec = saleDec;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getReportFlag() {
		return reportFlag;
	}

	public void setReportFlag(String reportFlag) {
		this.reportFlag = reportFlag;
	}

	public String getShipVia() {
		return shipVia;
	}

	public void setShipVia(String shipVia) {
		this.shipVia = shipVia;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getNumberCartons() {
		return numberCartons;
	}

	public void setNumberCartons(Double numberCartons) {
		this.numberCartons = numberCartons;
	}

	public Double getActualCost() {
		return actualCost;
	}

	public void setActualCost(Double actualCost) {
		this.actualCost = actualCost;
	}

	public String getToStores() {
		return toStores;
	}

	public void setToStores(String toStores) {
		this.toStores = toStores;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public Double getSobcRate() {
		return sobcRate;
	}

	public void setSobcRate(Double sobcRate) {
		this.sobcRate = sobcRate;
	}

	public Double getArbcRate() {
		return arbcRate;
	}

	public void setArbcRate(Double arbcRate) {
		this.arbcRate = arbcRate;
	}

	public String getPacklistFlag() {
		return packlistFlag;
	}

	public void setPacklistFlag(String packlistFlag) {
		this.packlistFlag = packlistFlag;
	}

	public LocalDateTime getDatelastEdi() {
		return datelastEdi;
	}

	public void setDatelastEdi(LocalDateTime datelastEdi) {
		this.datelastEdi = datelastEdi;
	}

	public LocalDateTime getPackslipDate() {
		return packslipDate;
	}

	public void setPackslipDate(LocalDateTime packslipDate) {
		this.packslipDate = packslipDate;
	}

	public String getSofstUser1() {
		return sofstUser1;
	}

	public void setSofstUser1(String sofstUser1) {
		this.sofstUser1 = sofstUser1;
	}

	public String getSofstUser2() {
		return sofstUser2;
	}

	public void setSofstUser2(String sofstUser2) {
		this.sofstUser2 = sofstUser2;
	}

	public LocalDateTime getReqshipDate() {
		return reqshipDate;
	}

	public void setReqshipDate(LocalDateTime reqshipDate) {
		this.reqshipDate = reqshipDate;
	}

	public LocalDateTime getShshipDate() {
		return shshipDate;
	}

	public void setShshipDate(LocalDateTime shshipDate) {
		this.shshipDate = shshipDate;
	}

	public LocalDateTime getRevshipDate() {
		return revshipDate;
	}

	public void setRevshipDate(LocalDateTime revshipDate) {
		this.revshipDate = revshipDate;
	}

	public Double getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(Double boxNumber) {
		this.boxNumber = boxNumber;
	}

	public Double getTotalBoxes() {
		return totalBoxes;
	}

	public void setTotalBoxes(Double totalBoxes) {
		this.totalBoxes = totalBoxes;
	}

	public String getPackshipId() {
		return packshipId;
	}

	public void setPackshipId(String packshipId) {
		this.packshipId = packshipId;
	}

	public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public LocalDateTime getDateModified() {
		return dateModified;
	}

	public void setDateModified(LocalDateTime dateModified) {
		this.dateModified = dateModified;
	}

	public String getTimelastUpdt() {
		return timelastUpdt;
	}

	public void setTimelastUpdt(String timelastUpdt) {
		this.timelastUpdt = timelastUpdt;
	}

	public LocalDateTime getDatelastUpdate() {
		return datelastUpdate;
	}

	public void setDatelastUpdate(LocalDateTime datelastUpdate) {
		this.datelastUpdate = datelastUpdate;
	}

	public Double getSocurrRate() {
		return socurrRate;
	}

	public void setSocurrRate(Double socurrRate) {
		this.socurrRate = socurrRate;
	}

	public Double getArcurrRate() {
		return arcurrRate;
	}

	public void setArcurrRate(Double arcurrRate) {
		this.arcurrRate = arcurrRate;
	}
	
	
	
	
	

}
