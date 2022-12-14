package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="item_type")
public class ItemType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name = "desc_item_type")
	private String descItemType;
	@Column(name = "type_action")
	private String typeAction;
	@Column(name = "stat_reg")
	private String statReg;
	
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDescItemType() {
		return descItemType;
	}
	
	public void setDescItemType(String descItemType) {
		this.descItemType = descItemType;
	}
	
		
	public String getTypeAction() {
		return typeAction;
	}

	public void setTypeAction(String typeAction) {
		this.typeAction = typeAction;
	}

	public String getStatReg() {
		return statReg;
	}
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}
	
	
}
