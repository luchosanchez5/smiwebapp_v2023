package com.model;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



public class V_notifications {
	

	private BigInteger id;
    private String comment;
    private Date dateNot;
    private Timestamp dateNot2;
    private String status;
    private String typeNotification;
	private BigInteger id_quote;
    private String nroRfq;
	private BigInteger id_user_note;
    private String stat_read_estimator;
    private String stat_read_seller;
	private BigInteger id_estimator ;
	private BigInteger id_seller ;
	private BigInteger id_user_est;
	private BigInteger id_user_seller;
    private String name_user_message;
    private String image;
    private String origin;
    
    
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getDateNot() {
		return dateNot;
	}
	public void setDateNot(Date dateNot) {
		this.dateNot = dateNot;
	}
	
		
	public Timestamp getDateNot2() {
		return dateNot2;
	}
	public void setDateNot2(Timestamp dateNot2) {
		this.dateNot2 = dateNot2;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTypeNotification() {
		return typeNotification;
	}
	public void setTypeNotification(String typeNotification) {
		this.typeNotification = typeNotification;
	}
	public BigInteger getId_quote() {
		return id_quote;
	}
	public void setId_quote(BigInteger id_quote) {
		this.id_quote = id_quote;
	}
	public String getNroRfq() {
		return nroRfq;
	}
	public void setNroRfq(String nroRfq) {
		this.nroRfq = nroRfq;
	}
	public BigInteger getId_user_note() {
		return id_user_note;
	}
	public void setId_user_note(BigInteger id_user_note) {
		this.id_user_note = id_user_note;
	}
	public String getStat_read_estimator() {
		return stat_read_estimator;
	}
	public void setStat_read_estimator(String stat_read_estimator) {
		this.stat_read_estimator = stat_read_estimator;
	}
	public String getStat_read_seller() {
		return stat_read_seller;
	}
	public void setStat_read_seller(String stat_read_seller) {
		this.stat_read_seller = stat_read_seller;
	}
	public BigInteger getId_estimator() {
		return id_estimator;
	}
	public void setId_estimator(BigInteger id_estimator) {
		this.id_estimator = id_estimator;
	}
	public BigInteger getId_seller() {
		return id_seller;
	}
	public void setId_seller(BigInteger id_seller) {
		this.id_seller = id_seller;
	}
	public BigInteger getId_user_est() {
		return id_user_est;
	}
	public void setId_user_est(BigInteger id_user_est) {
		this.id_user_est = id_user_est;
	}
	public BigInteger getId_user_seller() {
		return id_user_seller;
	}
	public void setId_user_seller(BigInteger id_user_seller) {
		this.id_user_seller = id_user_seller;
	}
	public String getName_user_message() {
		return name_user_message;
	}
	public void setName_user_message(String name_user_message) {
		this.name_user_message = name_user_message;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
    
	
    
    
    
    

}
