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
@Table(name="estimator")
public class Estimator {
	
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "ESTIMATOR_SEQ", sequenceName = "ESTIMATOR_SEQ", initialValue = 1, allocationSize=100)
    @GeneratedValue(strategy = SEQUENCE, generator = "ESTIMATOR_SEQ")
    private long id;
    @Column(name = "sure_name")
    private String sureName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_contact")
    private String emailContact;
    @Column(name = "phone_contact")
    private String phoneContact;
    @Column(name = "create_date")
    private LocalDateTime createdDate;
    @Column(name = "stat_estimator")
    private String statEstimator;
    @Column(name = "stat_reg")
    private String statReg;
    @Column(name = "img_url")
    private String imgUrl;
    @ManyToOne
	@JoinColumn(name="id_login",referencedColumnName = "id")	
	private Users user;    
    
	public long getId() {
		return id;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getSureName() {
		return sureName;
	}
	
	
	public void setSureName(String sureName) {
		this.sureName = sureName;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	public String getEmailContact() {
		return emailContact;
	}
	
	
	public void setEmailContact(String emailContact) {
		this.emailContact = emailContact;
	}
	
	
	public String getPhoneContact() {
		return phoneContact;
	}
	
	
	public void setPhoneContact(String phoneContact) {
		this.phoneContact = phoneContact;
	}
	
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	
	public String getStatEstimator() {
		return statEstimator;
	}
	
	
	public void setStatEstimator(String statEstimator) {
		this.statEstimator = statEstimator;
	}
	
	
	public String getStatReg() {
		return statReg;
	}
	
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}


	public String getImgUrl() {
		return imgUrl;
	}


	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
    
    
    

}
