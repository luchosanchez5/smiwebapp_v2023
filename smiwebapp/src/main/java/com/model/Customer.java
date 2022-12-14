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
@Table(name="customer")
public class Customer {
	
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "CUSTOMER_SEQ", sequenceName = "CUSTOMER_SEQ", initialValue = 1, allocationSize=1)
    @GeneratedValue(strategy = SEQUENCE, generator = "CUSTOMER_SEQ")
    private long id;
    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "cell_phone")
    private String cellPhone;
    @Column(name = "contact_name")
    private String contactName;
    @Column(name = "contact_phone")
    private String contactPhone;
    @Column(name = "contact_email")
    private String contactEmail;
    @Column(name = "web_site")
    private String webSite;
    @Column(name = "cutomer_Origin")
    private String cutomerOrigin;
    @Column(name = "create_date")
    private LocalDateTime createdDate;
    @Column(name = "tesla_sub_contrator")
	private String teslaSubContrator;
    @Column(name = "customer_notes")
	private String customerNotes;
    @ManyToOne
	@JoinColumn(name="id_user",referencedColumnName = "id")		
	private Users user;
    @Column(name = "stat_customer")
	private String statCustomer; //Set an Existing Client (0 = Active , 1 = Suspended, 2 = Waiting (When is a New Client))
    @Column(name = "stat_company")
    private String statCompany;  //Set if a Customer become as part as company.
    @Column(name = "stat_reg")
    private String statReg;      // Set Status Record it is Active or Logical Delete
    @Column(name = "modified_date")
    private LocalDateTime ModifiedDate;
    @Column(name = "id_company")
    private String idCompany;
    @Column(name = "fax_no")
    private String faxNo;
	
	
	public long getId() {
		return id;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
	public String getCellPhone() {
		return cellPhone;
	}
	
	
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	
	
	public String getContactName() {
		return contactName;
	}
	
	
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	
	public String getContactPhone() {
		return contactPhone;
	}
	
	
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	
	public String getContactEmail() {
		return contactEmail;
	}
	
	
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	
	
	public String getCutomerOrigin() {
		return cutomerOrigin;
	}
	
	
	public void setCutomerOrigin(String cutomerOrigin) {
		this.cutomerOrigin = cutomerOrigin;
	}
	
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	
	public String getTeslaSubContrator() {
		return teslaSubContrator;
	}
	
	
	public void setTeslaSubContrator(String teslaSubContrator) {
		this.teslaSubContrator = teslaSubContrator;
	}
	
	
	public String getCustomerNotes() {
		return customerNotes;
	}
	
	
	public void setCustomerNotes(String customerNotes) {
		this.customerNotes = customerNotes;
	}
	
	
	public Users getUser() {
		return user;
	}
	
	
	public void setUser(Users user) {
		this.user = user;
	}
	
	
	public String getStatCustomer() {
		return statCustomer;
	}
	
	
	public void setStatCustomer(String statCustomer) {
		this.statCustomer = statCustomer;
	}
	
	
	public String getStatCompany() {
		return statCompany;
	}
	
	
	public void setStatCompany(String statCompany) {
		this.statCompany = statCompany;
	}
	
	
	public String getStatReg() {
		return statReg;
	}
	
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}


	public String getWebSite() {
		return webSite;
	}


	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}


	public LocalDateTime getModifiedDate() {
		return ModifiedDate;
	}


	public void setModifiedDate(LocalDateTime modifiedDate) {
		ModifiedDate = modifiedDate;
	}


	public String getIdCompany() {
		return idCompany;
	}


	public void setIdCompany(String idCompany) {
		this.idCompany = idCompany;
	}


	public String getFaxNo() {
		return faxNo;
	}


	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}


    
	
	
	
	
	

    

}
