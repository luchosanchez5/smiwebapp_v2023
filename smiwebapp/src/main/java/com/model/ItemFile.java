package com.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="item_file")
public class ItemFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	@Column(name = "file_name")
	private String fileName;
	@Column(name = "file_type")
	private String fileType;
	@ManyToOne
	@JoinColumn(name="id_item",referencedColumnName = "id_item")		
	private Item item;
	@ManyToOne
	@JoinColumn(name="id_user",referencedColumnName = "id")		
	private Users user;
	@Column(name = "stat_reg")
	private String statReg;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileType() {
		return fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	
	public String getStatReg() {
		return statReg;
	}
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}
	
}
