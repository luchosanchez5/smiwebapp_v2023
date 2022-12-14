package com.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="item_activity")
public class ItemActivity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_item_activity")
	private long idItemActivity;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "desc_activity")
	private String descActivity;
	@ManyToOne
	@JoinColumn(name="id_item",referencedColumnName = "id_item")		
	private Item item;
	@ManyToOne
	@JoinColumn(name="id_user",referencedColumnName = "id")		
	private Users user;
	@Column(name = "stat_record")
	private String statRecord;
	@Column(name = "username")
	private String username;
	@Column(name = "stat_read_seller")
    private String stat_read_seller;
	@Column(name = "stat_read_estimator")
    private String stat_read_estimator;
	
	
	public long getIdItemActivity() {
		return idItemActivity;
	}
	public void setIdItemActivity(long idItemActivity) {
		this.idItemActivity = idItemActivity;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public String getDescActivity() {
		return descActivity;
	}
	public void setDescActivity(String descActivity) {
		this.descActivity = descActivity;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public String getStatRecord() {
		return statRecord;
	}
	public void setStatRecord(String statRecord) {
		this.statRecord = statRecord;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStat_read_seller() {
		return stat_read_seller;
	}
	public void setStat_read_seller(String stat_read_seller) {
		this.stat_read_seller = stat_read_seller;
	}
	public String getStat_read_estimator() {
		return stat_read_estimator;
	}
	public void setStat_read_estimator(String stat_read_estimator) {
		this.stat_read_estimator = stat_read_estimator;
	}
	
	
    
	
	

}
