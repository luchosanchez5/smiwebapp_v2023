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
@Table(name="quote")
public class Quote {
	
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "QUOTE_SEQ", sequenceName = "QUOTE_SEQ", initialValue = 1, allocationSize=1)
    @GeneratedValue(strategy = SEQUENCE, generator = "QUOTE_SEQ")
    private long id;
    public QuoteSubStatus getQuotesubStatus() {
		return quotesubStatus;
	}

	public void setQuotesubStatus(QuoteSubStatus quotesubStatus) {
		this.quotesubStatus = quotesubStatus;
	}

	@Column(name = "nro_rfq")
    private String nroRfq;
    @Column(name = "note_obtain")
    private String noteObtain;
    @Column(name = "tot_items")
    private int totItems;
	@ManyToOne
	@JoinColumn(name="id_seller",referencedColumnName = "id")		
	private Seller seller ;
	@ManyToOne
	@JoinColumn(name="id_estimator",referencedColumnName = "id")		
	private Estimator estimator ;
	@ManyToOne
	@JoinColumn(name="id_referral",referencedColumnName = "id")		
	private Referral referral ;
	@ManyToOne
	@JoinColumn(name="id_term",referencedColumnName = "id")		
	private Terms terms;	
	@ManyToOne
	@JoinColumn(name="id_user",referencedColumnName = "id")	
	private Users user;	
	@ManyToOne
	@JoinColumn(name="id_customer",referencedColumnName = "id")	
	private Customer customer;
	@ManyToOne
	@JoinColumn(name="id_substatus",referencedColumnName = "id")	
	private QuoteSubStatus quotesubStatus;
	@Column(name = "quote_notes")
	private String quoteNotes;
	@Column(name = "created_date")
    private LocalDateTime createdDate;
	@Column(name = "new_customer")
    private String newCustomer;
	@Column(name = "stat_quote")
    private String statQuote;
	@Column(name = "stat_reg")
    private String statReg;
	@Column(name = "print_materials")
    private String printMaterials;
	@Column(name = "print_tolerance")
    private String printTolerance;
	@Column(name = "print_lead_time")
    private String printLeadTime;
	@Column(name = "print_fob")
    private String printFOB;
	
	@Column(name = "lastupdate_date")
	private LocalDateTime lastupdateDate;
	@ManyToOne
	@JoinColumn(name="lastupdate_user",referencedColumnName = "id")
	private Users lastupdateUser;
	@Column(name = "deleted_date")
	private LocalDateTime deletedDate;
	@ManyToOne
	@JoinColumn(name="deleted_user",referencedColumnName = "id")
	private Users deletedUser;
	@Column(name = "order_received")     // Y or N
    private String orderReceived;
	@Column(name = "date_order_received")
    private LocalDateTime dateorderReceived;
	@Column(name = "last_status")
    private String lastStatus;     // Control last status before updtae to a new stat.
	
	//----------------   New Fields For Requote   10/25/2019 -----------------------------
	
	@ManyToOne
	@JoinColumn(name="requote_user",referencedColumnName = "id")
	private Users requoteUser;
	
	@Column(name = "requote_date")
	private LocalDateTime requoteDate;
	
	@Column(name = "requote_status")    // Y or N
    private String requoteStatus;
	
	@Column(name = "requote_revision")  // A, B, C, D, E , F
    private String requoteRevision;
	
	@Column(name = "requote_total")  // A, B, C, D, E , F
    private int requoteTotal;
	
	@Column(name = "moq")  // A, B, C, D, E , F
    private String moq;
	
	//----------------------------------------------------------------
	
	@Column(name = "contact_email")
    private String contactEmail;     // Control last status before updtae to a new stat.
	
	@Column(name = "contact_phone")
    private String contactPhone;     // Control last status before updtae to a new stat.
	
	@Column(name = "contact_name")
    private String contactName;     // Control last status before updtae to a new stat.	
	
	@Column(name = "setorder_items", columnDefinition="character varying default '0'")
	private String setorderItems;
	
	@Column(name = "id_rejected")
    private String idRejected;
	
	@Column(name = "note_rejected")
    private String noteRejected;	
	
	@Column(name = "sop_year")
    private String sopYear;
	
	@ManyToOne
	@JoinColumn(name="feedback_user",referencedColumnName = "id")
	private Users feedbackUser;
	
	@Column(name = "lastfeedback_date")
	private LocalDateTime lastfeedbackDate;
	
	@Column(name = "expiration_days")
    private String expirationDays;	
	
	
	@ManyToOne
	@JoinColumn(name="id_seller2",referencedColumnName = "id")		
	private Seller seller2 ;
	
	@Column(name = "sharedQuote", columnDefinition="character varying default '0'")
    private String sharedQuote;	
	
	@ManyToOne
	@JoinColumn(name="id_incoterms",referencedColumnName = "id")		
	private Incoterms incoTerms;
	
	@Column(name = "sales_code")
    private String salesCode;
	
       

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getNroRfq() {
		return nroRfq;
	}
	
	public void setNroRfq(String nroRfq) {
		this.nroRfq = nroRfq;
	}
	
	public String getNoteObtain() {
		return noteObtain;
	}
	
	public void setNoteObtain(String noteObtain) {
		this.noteObtain = noteObtain;
	}
	
	public int getTotItems() {
		return totItems;
	}
	
	public void setTotItems(int totItems) {
		this.totItems = totItems;
	}
	
	public Seller getSeller() {
		return seller;
	}
	
	public void setSeller(Seller seller) {
		this.seller = seller;
	}
	
	public Estimator getEstimator() {
		return estimator;
	}
	
	public void setEstimator(Estimator estimator) {
		this.estimator = estimator;
	}
	
	public Terms getTerms() {
		return terms;
	}
	
	public void setTerms(Terms terms) {
		this.terms = terms;
	}
	
	public Users getUser() {
		return user;
	}
	
	public void setUser(Users user) {
		this.user = user;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getNewCustomer() {
		return newCustomer;
	}
	
	public void setNewCustomer(String newCustomer) {
		this.newCustomer = newCustomer;
	}
	
	
	public String getStatQuote() {
		return statQuote;
	}
	
	public void setStatQuote(String statQuote) {
		this.statQuote = statQuote;
	}

	public String getStatReg() {
		return statReg;
	}

	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}

	public Referral getReferral() {
		return referral;
	}

	public void setReferral(Referral referral) {
		this.referral = referral;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getQuoteNotes() {
		return quoteNotes;
	}

	public void setQuoteNotes(String quoteNotes) {
		this.quoteNotes = quoteNotes;
	}

	public String getPrintMaterials() {
		return printMaterials;
	}

	public void setPrintMaterials(String printMaterials) {
		this.printMaterials = printMaterials;
	}

	public String getPrintTolerance() {
		return printTolerance;
	}

	public void setPrintTolerance(String printTolerance) {
		this.printTolerance = printTolerance;
	}

	public String getPrintLeadTime() {
		return printLeadTime;
	}

	public void setPrintLeadTime(String printLeadTime) {
		this.printLeadTime = printLeadTime;
	}

	public String getPrintFOB() {
		return printFOB;
	}

	public void setPrintFOB(String printFOB) {
		this.printFOB = printFOB;
	}

	public LocalDateTime getLastupdateDate() {
		return lastupdateDate;
	}

	public void setLastupdateDate(LocalDateTime lastupdateDate) {
		this.lastupdateDate = lastupdateDate;
	}

	public Users getLastupdateUser() {
		return lastupdateUser;
	}

	public void setLastupdateUser(Users lastupdateUser) {
		this.lastupdateUser = lastupdateUser;
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}

	public Users getDeletedUser() {
		return deletedUser;
	}

	public void setDeletedUser(Users deletedUser) {
		this.deletedUser = deletedUser;
	}

	public String getOrderReceived() {
		return orderReceived;
	}

	public void setOrderReceived(String orderReceived) {
		this.orderReceived = orderReceived;
	}

	public LocalDateTime getDateorderReceived() {
		return dateorderReceived;
	}

	public void setDateorderReceived(LocalDateTime dateorderReceived) {
		this.dateorderReceived = dateorderReceived;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public Users getRequoteUser() {
		return requoteUser;
	}

	public void setRequoteUser(Users requoteUser) {
		this.requoteUser = requoteUser;
	}

	public LocalDateTime getRequoteDate() {
		return requoteDate;
	}

	public void setRequoteDate(LocalDateTime requoteDate) {
		this.requoteDate = requoteDate;
	}

	public String getRequoteStatus() {
		return requoteStatus;
	}

	public void setRequoteStatus(String requoteStatus) {
		this.requoteStatus = requoteStatus;
	}

	public String getRequoteRevision() {
		return requoteRevision;
	}

	public void setRequoteRevision(String requoteRevision) {
		this.requoteRevision = requoteRevision;
	}

	public int getRequoteTotal() {
		return requoteTotal;
	}

	public void setRequoteTotal(int requoteTotal) {
		this.requoteTotal = requoteTotal;
	}

	public String getMoq() {
		return moq;
	}

	public void setMoq(String moq) {
		this.moq = moq;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getSetorderItems() {
		return setorderItems;
	}

	public void setSetorderItems(String setorderItems) {
		this.setorderItems = setorderItems;
	}

	public String getIdRejected() {
		return idRejected;
	}

	public void setIdRejected(String idRejected) {
		this.idRejected = idRejected;
	}

	public String getNoteRejected() {
		return noteRejected;
	}

	public void setNoteRejected(String noteRejected) {
		this.noteRejected = noteRejected;
	}

	public String getSopYear() {
		return sopYear;
	}

	public void setSopYear(String sopYear) {
		this.sopYear = sopYear;
	}

	public LocalDateTime getLastfeedbackDate() {
		return lastfeedbackDate;
	}

	public void setLastfeedbackDate(LocalDateTime lastfeedbackDate) {
		this.lastfeedbackDate = lastfeedbackDate;
	}

	public Users getFeedbackUser() {
		return feedbackUser;
	}

	public void setFeedbackUser(Users feedbackUser) {
		this.feedbackUser = feedbackUser;
	}

	public String getExpirationDays() {
		return expirationDays;
	}

	public void setExpirationDays(String expirationDays) {
		this.expirationDays = expirationDays;
	}

	public Seller getSeller2() {
		return seller2;
	}

	public void setSeller2(Seller seller2) {
		this.seller2 = seller2;
	}

	public String getSharedQuote() {
		return sharedQuote;
	}

	public void setSharedQuote(String sharedQuote) {
		this.sharedQuote = sharedQuote;
	}

	public Incoterms getIncoTerms() {
		return incoTerms;
	}

	public void setIncoTerms(Incoterms incoTerms) {
		this.incoTerms = incoTerms;
	}

	public String getSalesCode() {
		return salesCode;
	}

	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}


	
		
	
    
}
