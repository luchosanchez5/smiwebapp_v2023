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
@Table(name="quote_note")
public class QuoteNote {
	
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "QUOTENOTE_SEQ", sequenceName = "QUOTENOTE_SEQ", initialValue = 1, allocationSize=1)
    @GeneratedValue(strategy = SEQUENCE, generator = "QUOTENOTE_SEQ")
    private long id;
    @Column(name = "created_date")
    private LocalDateTime createdDate;
	@ManyToOne
	@JoinColumn(name="id_user",referencedColumnName = "id")		
	private Users user;	
	@ManyToOne
	@JoinColumn(name="id_quote",referencedColumnName = "id")		
	private Quote quote;
	@Column(name = "comment")
    private String comment;
	@Column(name = "stat_reg")
    private String statReg;
	@Column(name = "type_note")
	private String typeNote;   //F : Follow Up   , S: System
	@Column(name = "stat_read_seller")
    private String stat_read_seller;
	@Column(name = "stat_read_estimator")
    private String stat_read_estimator;
	
    
    
    
    
	public QuoteNote() {
		
		// TODO Auto-generated constructor stub
	}

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
	
	public Users getUser() {
		return user;
	}
	
	public void setUser(Users user) {
		this.user = user;
	}
	
	public Quote getQuote() {
		return quote;
	}
	
	public void setQuote(Quote quote) {
		this.quote = quote;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getStatReg() {
		return statReg;
	}
	
	public void setStatReg(String statReg) {
		this.statReg = statReg;
	}

	public String getTypeNote() {
		return typeNote;
	}

	public void setTypeNote(String typeNote) {
		this.typeNote = typeNote;
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
