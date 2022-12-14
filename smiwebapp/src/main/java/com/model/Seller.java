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
@Table(name="seller")
public class Seller {
	
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SELLER_SEQ", sequenceName = "SELLER_SEQ", initialValue = 1, allocationSize=100)
    @GeneratedValue(strategy = SEQUENCE, generator = "SELLER_SEQ")
    private long id;
    @Column(name = "sure_name")
    private String sureName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email_contact")
    private String emailContact;
    @Column(name = "phone_contact")
    private String phoneContact;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "stat_seller")
    private String statSeller;
    @Column(name = "stat_reg")
    private String statReg;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "id_user")
    private String idUser;	
    @ManyToOne
	@JoinColumn(name="id_login",referencedColumnName = "id")	
	private Users user;
    @Column(name = "ubication")
    private String ubication;
    @Column(name = "posittion")
    private String posittion;
    @Column(name = "notification")
    private String notification;
    
    
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
	
	public String getStatSeller() {
		return statSeller;
	}
	
	public void setStatSeller(String statSeller) {
		this.statSeller = statSeller;
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


	public String getIdUser() {
		return idUser;
	}


	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}


	public Users getUser() {
		return user;
	}


	public void setUser(Users user) {
		this.user = user;
	}


	public String getUbication() {
		return ubication;
	}


	public void setUbication(String ubication) {
		this.ubication = ubication;
	}


	public String getPosittion() {
		return posittion;
	}


	public void setPosittion(String posittion) {
		this.posittion = posittion;
	}


	public String getNotification() {
		return notification;
	}


	public void setNotification(String notification) {
		this.notification = notification;
	}
	
	
	
	
    
}
