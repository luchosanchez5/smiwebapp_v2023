package com.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="users")
public class Users {
	
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", initialValue = 1, allocationSize=100)
    @GeneratedValue(strategy = SEQUENCE, generator = "USERS_SEQ")
    private Long id;

    @NotNull
    @Column(name = "user_name")
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "status")
    private int status;
    
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = CascadeType.ALL)
    private Set<UserRole> roles = new HashSet<>();
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "user_type")
    private String userType;
    
    @Column(name = "mobil_phone")
    private String mobilPhone;   
    
    //Profile Options
    
    @Column(name = "parts_autocomplete")
    private String partsAutocomplete;   //Show or Not Autocomplete Parts
    
    @Column(name = "ip_filter")
    private String ipFilter;   //Show or Not Autocomplete Parts  
    
    @Column(name = "ip_address")
    private String ipAddress;   //Show or Not Autocomplete Parts     
    
    @Column(name = "notification")
    private String notification;   //Show or Not Autocomplete Parts    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getMobilPhone() {
		return mobilPhone;
	}

	public void setMobilPhone(String mobilPhone) {
		this.mobilPhone = mobilPhone;
	}

	public String getPartsAutocomplete() {
		return partsAutocomplete;
	}

	public void setPartsAutocomplete(String partsAutocomplete) {
		this.partsAutocomplete = partsAutocomplete;
	}

	public String getIpFilter() {
		return ipFilter;
	}

	public void setIpFilter(String ipFilter) {
		this.ipFilter = ipFilter;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getNotification() {
		return notification;
	}

	public void setNotification(String notification) {
		this.notification = notification;
	}


	
	
	
	
    
    

}
