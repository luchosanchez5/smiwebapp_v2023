package com.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="file_upload")
public class FileUpload {

    public FileUpload(String filename, byte[] file, String mimeType) {

        this.file = file;
        this.filename = filename;
        this.mimeType = mimeType;
    }

    public FileUpload() {
        // Default Constructor
    }
    
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "FILE_SEQ", sequenceName = "FILE_SEQ", initialValue = 1, allocationSize=100)
    @GeneratedValue(strategy = SEQUENCE, generator = "FILE_SEQ")
    private long id;
    @Column(name = "filename")
    private String filename;

    //@Lob
    @Column(name = "file")
    private byte[] file;
    @Column(name = "mime_type")
    private String mimeType;
    
	@ManyToOne
	@JoinColumn(name="id_quote",referencedColumnName = "id")		
	private Quote quote;
	@Column(name = "session_id")
	private String sessionId;
	@ManyToOne
	@JoinColumn(name="id_user",referencedColumnName = "id")
	private Users users;
	@Column(name = "stat_file")
	private String statFile;
	
	@Column(name = "created_date")
	private LocalDateTime createdDate;
	
	

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getStatFile() {
		return statFile;
	}

	public void setStatFile(String statFile) {
		this.statFile = statFile;
	}
	
	
	
	
    
    
}
